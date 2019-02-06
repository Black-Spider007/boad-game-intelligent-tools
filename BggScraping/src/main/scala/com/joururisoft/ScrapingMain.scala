package com.joururisoft

import java.io.{FileOutputStream, OutputStreamWriter}
import net.ruippeixotog.scalascraper.model.Element

object ScrapingMain extends GameLinkList {
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

          logger.info(s"出力対象 URL：$BASE_URL$link")
          val fileName = s"output-files/${basicOrExtension}_${gameId}_$gameTitle.html"
          val encode = "UTF-8"
          val append = true

          // 書き込み処理
          val fileOutPutStream = new FileOutputStream(fileName, append)
          val writer = new OutputStreamWriter(fileOutPutStream, encode)

          driver.get(s"$BASE_URL$link")
          writer.write(driver.getPageSource)
          writer.close()
          logger.info(s"出力完了")
        }
      case None => //
    }
  }
}
