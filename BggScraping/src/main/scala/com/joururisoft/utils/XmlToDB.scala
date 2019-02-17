package com.joururisoft.utils

import java.time.{LocalDate, ZonedDateTime}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

import com.joururisoft.models.BoardGameMst
import com.typesafe.scalalogging.LazyLogging

import scala.xml.Node

object XmlToDB extends LazyLogging {
  def insertGameMst(statsXml: Node): Unit = {
    val gameMstId = (statsXml \\ "item" \ "@id").text.toInt
    if (BoardGameMst.find(gameMstId).isDefined) {
      logger.info(s"既に存在する ID のためスキップします：$gameMstId")
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
      minAge = minAge
    )
  }

  def insertStaticInfo(statsXml: Node): Unit = {
    val usersRated = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val bayesAverageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val stdEviation = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }
    val averageRate = (statsXml \\ "usersrated" \ "@value").text match {
      case "" => None
      case x => Some(x.toInt)
    }

    usersRated: Option[Int] = None,
    averageRate: Option[Double] = None,
    bayesAverageRate: Option[Double] = None,
    stdEviation: Option[Double] = None,
    median: Option[Int] = None,
    owned: Option[Int] = None,
    trading: Option[Int] = None,
    wanting: Option[Int] = None,
    wishing: Option[Int] = None,
    numOfComments: Option[Int] = None,
    numOfWeights: Option[Int] = None,
    averageWeight: Option[Double] = None,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None

  }
}
