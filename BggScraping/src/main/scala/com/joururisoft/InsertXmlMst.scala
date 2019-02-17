package com.joururisoft

import com.joururisoft.models.XmlMst
import net.ruippeixotog.scalascraper.model.Element

import scala.xml.{Node, XML}
import scalikejdbc._

object InsertXmlMst extends GameLinkList with BGAConnection {
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

          if (gameId != "unknownId" && XmlMst.findBy(sqls"game_id = ${gameId.toInt}").isEmpty) {
            insertGameMstAndStaticInfoXml(gameId.toInt)
//            insertCommentsMst(gameId.toInt)
          }
        }
      case None => //
    }
  }

  private def insertGameMstAndStaticInfoXml(gameId: Int): Unit = {
    val statsApiUrl = s"https://www.boardgamegeek.com/xmlapi2/thing?id=$gameId&stats=1"
    logger.info(s"XML マスタ URL：$statsApiUrl を出力します")
    driver.get(statsApiUrl)
    val statsXml = XML.loadString(driver.getPageSource)
    if ((statsXml \\ "item" \ "@id").text.isEmpty) {
      Thread.sleep(2345)
      insertGameMstAndStaticInfoXml(gameId)
    } else {
      insertXmlMst(statsXml)
      logger.info(s"XML マスタ($statsApiUrl) 出力完了")
    }
  }

  // 使ってない
  private def insertCommentsMst(gameId: Int): Unit = {
    val commentUrl = s"https://www.boardgamegeek.com/xmlapi2/thing?id=$gameId&comments=1"
    driver.get(commentUrl)
    val commentXml = XML.loadString(driver.getPageSource)
    val totalComentSize = (commentXml \\ "comments" \ "@totalitems").text match {
      case "" => 0
      case x => x.toInt
    }
  }

  private def insertXmlMst(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt

    XmlMst.create(
      gameId = gameMstId,
      xmlType = "static",
      page = 1,
      xmlMst = Option(statsXml.toString)
    )
  }
}
