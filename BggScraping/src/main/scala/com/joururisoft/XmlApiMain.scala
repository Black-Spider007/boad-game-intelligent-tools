package com.joururisoft

import com.joururisoft.ScrapingMain.driver
import net.ruippeixotog.scalascraper.model.Element

import scala.io.Source
import scala.xml.XML

object XmlApiMain extends GameLinkList {
  override def processGameLinkList(gameLinkList: Option[List[Element]]): Unit = {
    gameLinkList match {
      case Some(list) =>
        list.foreach { elem =>
          val link = elem.attr("href")
          val path = link.split("""/""")

          val (basicOrExtension, gameId, gameTitle) = path.length match {
            case 4 => (path(1), path(2), path(3))
            case 3 => (path(1), path(2), "unknownTitle")
            case _ => ("unknownExtension", "unknownId", "unknownTitle")
          }

          if (gameId != "unknownId") {
            val statsApiUrl = s"https://www.boardgamegeek.com/xmlapi2/thing?id=$gameId?stats=1"

            // 文字列からXMLオブジェクトを作る
            driver.get(statsApiUrl)
            val statsXml = XML.loadString(driver.getPageSource)

            logger.info(s"概要 API URL：$statsApiUrl")
            logger.info(statsXml.toString)

            logger.info(s"出力完了")
          }
        }
      case None => //
    }
  }
}
