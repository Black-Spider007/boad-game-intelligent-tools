package com.joururisoft

import com.typesafe.scalalogging.LazyLogging
import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

object ScrapingMain extends App with LazyLogging {
  val BASE_URL = "https://boardgamegeek.com"
  val BROWSE_BOADGAME = "/browse/boardgame"

  scrapingMain()

  def scrapingMain(): Unit = {
    val jSoupBrowser = JsoupBrowser()

    val doc = jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME")
    val lastPage = (doc >> text("""a[title="last page"]"""))
      .toString
      .replaceAll("\\[|\\]", "")
      .toInt

    val pageNumberList = (1 to lastPage).toList

    pageNumberList.foreach { pageNumber =>
      logger.info(s"URL : $BASE_URL$BROWSE_BOADGAME/page/$pageNumber")
      val gameListDoc = jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME/page/$pageNumber")
      val gameLinkList = gameListDoc >?> elementList("tr#row_ td.collection_objectname a")

      gameLinkList match {
        case Some(list) =>
          list.foreach { elem =>
            val link = elem.attr("href")
            println(jSoupBrowser.get(s"$BASE_URL$link"))
          }
        case None => //
      }
    }
  }
}
