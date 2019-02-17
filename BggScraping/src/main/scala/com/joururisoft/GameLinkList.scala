package com.joururisoft

import java.net.SocketTimeoutException

import com.typesafe.scalalogging.LazyLogging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{elementList, text}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import org.openqa.selenium.phantomjs.{PhantomJSDriver, PhantomJSDriverService}
import org.openqa.selenium.remote.DesiredCapabilities

trait GameLinkList extends App with LazyLogging {
  val BASE_URL = "https://boardgamegeek.com"
  val BROWSE_BOADGAME = "/browse/boardgame"
  val userAgent = "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1"
  System.setProperty("phantomjs.page.settings.userAgent", userAgent)

  val caps = new DesiredCapabilities
  caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, """D:\Program Files\phantomjs-2.1.1-windows\bin\phantomjs""")
  val driver = new PhantomJSDriver(caps)

  try {
    scrapingMain()
  } finally {
    driver.quit()
  }

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
      val gameListDoc = try { // 2回までリトライ
        jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME/page/$pageNumber")
      } catch {
        case e: SocketTimeoutException =>
          logger.error(e.getMessage)
          logger.error("wait 5000 milli sec.")
          Thread.sleep(5000)
          try {
            jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME/page/$pageNumber")
          } catch {
            case e: SocketTimeoutException =>
              logger.error(e.getMessage)
              logger.error("wait 5000 milli sec.")
              Thread.sleep(5000)
              jSoupBrowser.get(s"$BASE_URL$BROWSE_BOADGAME/page/$pageNumber")
          }
      }
      val gameLinkList = gameListDoc >?> elementList("tr#row_ td.collection_objectname a")

      processGameLinkList(gameLinkList)
    }
  }

  def processGameLinkList(gameLinkList: Option[List[Element]]): Unit
}