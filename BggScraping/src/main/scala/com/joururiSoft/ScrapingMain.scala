package com.joururiSoft

import com.typesafe.scalalogging.LazyLogging
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

object ScrapingMain extends App with LazyLogging {
  val BASE_URL = "https://boardgamegeek.com"

  scrapingMain()

  def scrapingMain(): Unit = {
    val htmlUnitBrowser = HtmlUnitBrowser()

    val doc = htmlUnitBrowser.get(s"$BASE_URL/browse/boardgame")
    val lastPage = (doc >> text("""a[title="last page"]"""))
      .toString
      .replaceAll("\\[|\\]", "")
      .toInt

    val pageNumberList = (1 to lastPage).toList

    pageNumberList.foreach { pageNumber =>
      logger.info(pageNumber.toString)
    }
  }
}
