package com.joururisoft.genreprocessor

import com.joururisoft.{BGAConnection, BGGScrapingHeader}
import com.joururisoft.utils.CommonUtils
import com.typesafe.scalalogging.LazyLogging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{attr, elementList, text}
import net.ruippeixotog.scalascraper.dsl.DSL._

trait GameGenre extends App with LazyLogging with BGGScrapingHeader with CommonUtils with BGAConnection {
  scrapingMain()

  def scrapingMain(): Unit = {
    val jSoupBrowser = JsoupBrowser()

    val doc = jSoupBrowser.get(s"$BASE_URL${targetLink()}")

    val targetNameList = (doc >> elementList(".forum_table tbody tr td p"))
      .filterNot { p =>
        (p >?> text("strong")).isDefined || p.text.isEmpty
      }

    val descriptionJpList = (doc >> elementList(".forum_table tbody tr td dl dd"))
      .filterNot { p =>
        (p >?> text("strong")).isDefined || p.text.isEmpty
      }

    val targetList = targetNameList zip descriptionJpList

    truncateMst()

    targetList.foreach { targetTuple =>
      val nameElement = targetTuple._1
      val descriptionElement = targetTuple._2

      val nameEn = nameElement >> text("a")
      val nameJp = (nameElement >> text).replace(nameEn, "").trim
      val descriptionJp = descriptionElement >> text

      val englishLink = nameElement >> attr("href")("a")

      val targetId = englishLink.split("/")(2).toInt

      val descriptionEn = jSoupBrowser.get(s"$BASE_URL$englishLink") >> text("#editdesc")

      logger.info(s"targetId : $targetId")
      logger.info(s"nameEn : $nameEn")
      logger.info(s"nameJp : $nameJp")
      logger.info(s"descriptionEn : $descriptionEn")
      logger.info(s"descriptionJp : $descriptionJp")

      insertData(
        nameEn = nameEn,
        nameJp = nameJp,
        descriptionEn = descriptionEn,
        descriptionJp = descriptionJp,
        targetId = targetId
      )
    }
  }

  protected def insertData(nameEn: String,
                           nameJp: String,
                           descriptionEn: String,
                           descriptionJp: String,
                           targetId: Int
                          ): Unit

  protected def targetLink(): String

  protected def truncateMst(): Unit
}
