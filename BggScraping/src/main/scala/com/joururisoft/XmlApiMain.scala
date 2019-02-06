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

          if (gameId == "unknownId") {
            val commentsApiUrl = s"http://www.boardgamegeek.com/xmlapi/boardgame/$gameId?comments=1"
            val statsApiUrl = s"http://www.boardgamegeek.com/xmlapi/boardgame/$gameId?stats=1"

            // 文字列からXMLオブジェクトを作る
            driver.get(commentsApiUrl)
            val commentsXml = XML.loadString(driver.getPageSource)
            driver.get(statsApiUrl)
            val statsXml = XML.loadString(driver.getPageSource)

            logger.info(s"コメント API URL：$commentsApiUrl")
            logger.info(commentsXml.toString)
            logger.info(s"概要 API URL：$statsApiUrl")
            logger.info(statsXml.toString)

            logger.info(s"出力完了")
          }
        }
      case None => //
    }
  }
}
