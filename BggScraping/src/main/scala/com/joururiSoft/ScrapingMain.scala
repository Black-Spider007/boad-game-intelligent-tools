package com.joururiSoft

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
      val gameListDoc = jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME/$pageNumber")
      val gameTable = gameListDoc >?> element("table.collection_table")
      
      logger.info(pageNumber.toString)
    }
  }
}
