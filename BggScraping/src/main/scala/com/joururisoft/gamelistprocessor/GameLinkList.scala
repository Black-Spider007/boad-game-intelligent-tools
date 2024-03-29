package com.joururisoft.gamelistprocessor

import java.net.SocketTimeoutException

import com.joururisoft.BGGScrapingHeader
import com.typesafe.scalalogging.LazyLogging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{elementList, text}
import org.openqa.selenium.phantomjs.{PhantomJSDriver, PhantomJSDriverService}
import org.openqa.selenium.remote.DesiredCapabilities
import net.ruippeixotog.scalascraper.dsl.DSL._

trait GameLinkList extends App with LazyLogging with BGGScrapingHeader {
  val BROWSE_BOADGAME = "/browse/boardgame"
  val caps = new DesiredCapabilities
  caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getenv("PHANTOMJS_HOME") + "/phantomjs")
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
