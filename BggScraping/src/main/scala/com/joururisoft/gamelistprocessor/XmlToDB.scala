package com.joururisoft.gamelistprocessor

import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeParseException}

import com.joururisoft.BGAConnection
import com.joururisoft.models.{BoardGameMst, CategoryRelation, MechanicsRelation, StaticInfo}
import com.joururisoft.utils.CommonUtils
import com.typesafe.scalalogging.LazyLogging

import scala.xml.Node
import scalikejdbc._

object XmlToDB extends LazyLogging with CommonUtils with BGAConnection {
  def insertGameMst(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt
    if (BoardGameMst.find(gameMstId).isDefined) {
      logger.info(s"BoardGameMst に既に存在する ID のためスキップします：$gameMstId")
      return
    }

    val name = ((statsXml \\ "name").theSeq
      .filter(node => (node \\ "@type").text == "primary")
      .head \\ "@value")
      .text
    val thumbnail = (statsXml \\ "thumbnail").text
    val image = (statsXml \\ "image").text
    val description = (statsXml \\ "description").text
    val publishedYearString = (statsXml \\ "yearpublished" \ "@value").text
    val parsable = try {
      LocalDate.parse(
        s"${publishedYearString}0101",
        DateTimeFormatter.ofPattern("yyyyMMdd")
      )
      true
    } catch {
      case e: DateTimeParseException =>
        logger.warn(e.getMessage)
        false
    }
    val publishedYear = if (parsable) {
      Some(LocalDate.parse(s"${publishedYearString}0101", DateTimeFormatter.ofPattern("yyyyMMdd")))
    } else {
      None
    }
    val minPlayers = (statsXml \\ "minplayers" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val maxPlayers = (statsXml \\ "maxplayers" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val playingTime = (statsXml \\ "playingtime" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val minPlayTime = (statsXml \\ "minplaytime" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val maxPlayTime = (statsXml \\ "maxplaytime" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val minAge = (statsXml \\ "minage" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }

    BoardGameMst.create(
      id = gameMstId,
      name = name,
      orgThumbnailUrl = Option(thumbnail),
      orgMainImageUrl = Option(image),
      description = Option(description),
      publishedYear = publishedYear,
      minPlayers = minPlayers,
      maxPlayers = maxPlayers,
      playingTime = playingTime,
      minPlayTime = minPlayTime,
      maxPlayTime = maxPlayTime,
      minAge = minAge,
      insertDate = Some(createZonedNow()),
      lastUpdate = Some(createZonedNow())
    )
  }

  def insertStaticInfo(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt
    val where = sqls"game_id = $gameMstId"
    if (StaticInfo.findBy(where).isDefined) {
      logger.info(s"StaticInfo に既に存在するゲーム ID のためスキップします：$gameMstId")
      return
    }

    val staticInfoNode = statsXml \\ "statistics"

    val usersRated = (staticInfoNode \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (staticInfoNode \\ "average" \ "@value").text match {
      case "" => None
      case x => Some(x.toDouble)
    }
    val bayesAverageRate = (staticInfoNode \\ "bayesaverage" \ "@value").text match {
      case "" => None
      case x => Some(x.toDouble)
    }
    val stdDeviation = (staticInfoNode \\ "stddev" \ "@value").text match {
      case "" => None
      case x => Some(x.toDouble)
    }
    val median = (staticInfoNode \\ "median" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val owned = (staticInfoNode \\ "owned" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val trading = (staticInfoNode \\ "trading" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val wanting = (staticInfoNode \\ "wanting" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val wishing = (staticInfoNode \\ "wishing" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val numOfComments = (staticInfoNode \\ "numcomments" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val numOfWeights = (staticInfoNode \\ "numweights" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageWeight = (staticInfoNode \\ "averageweight" \ "@value").text match {
      case "" => None
      case x => Some(x.toDouble)
    }

    StaticInfo.create(
      gameId = gameMstId,
      usersRated = usersRated,
      averageRate = averageRate,
      bayesAverageRate = bayesAverageRate,
      stdDeviation = stdDeviation,
      median = median,
      owned = owned,
      trading = trading,
      wanting = wanting,
      wishing = wishing,
      numOfComments = numOfComments,
      numOfWeights = numOfWeights,
      averageWeight = averageWeight,
      insertDate = Some(createZonedNow()),
      lastUpdate = Some(createZonedNow())
    )
  }

  def insertMechanicsRelation(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt
    val mechanicsIdSeq = (statsXml \\ "link").theSeq
        .filter(node => (node \\ "@type").text == "boardgamemechanic")
        .map(node => (node \\ "@id").text.toInt)

    val now = createZonedNow()

    mechanicsIdSeq.foreach { mechanicsId =>
      MechanicsRelation.create(
        gameId = gameMstId,
        mechanicsId = mechanicsId,
        insertDate = Some(now),
        lastUpdate = Some(now)
      )
    }
  }

  def insertCategoryRelation(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt
    val categoryIdSeq = (statsXml \\ "link").theSeq
      .filter(node => (node \\ "@type").text == "boardgamecategory")
      .map(node => (node \\ "@id").text.toInt)

    val now = createZonedNow()

    categoryIdSeq.foreach { categoryId =>
      CategoryRelation.create(
        gameId = gameMstId,
        categoryId = categoryId,
        insertDate = Some(now),
        lastUpdate = Some(now)
      )
    }
  }
}
