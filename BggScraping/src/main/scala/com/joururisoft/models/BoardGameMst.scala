package com.joururisoft.models

import scalikejdbc._
import java.time.{LocalDate, ZonedDateTime}

case class BoardGameMst(
  id: Int,
  parentId: Option[Int] = None,
  name: String,
  orgThumbnailUrl: Option[String] = None,
  orgMainImageUrl: Option[String] = None,
  description: Option[String] = None,
  publishedYear: Option[LocalDate] = None,
  minPlayers: Option[Int] = None,
  maxPlayers: Option[Int] = None,
  playingTime: Option[Int] = None,
  minPlayTime: Option[Int] = None,
  maxPlayTime: Option[Int] = None,
  minAge: Option[Int] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = BoardGameMst.autoSession): BoardGameMst = BoardGameMst.save(this)(session)

  def destroy()(implicit session: DBSession = BoardGameMst.autoSession): Int = BoardGameMst.destroy(this)(session)

}


object BoardGameMst extends SQLSyntaxSupport[BoardGameMst] {

  override val tableName = "board_game_mst"

  override val columns = Seq("id", "parent_id", "name", "org_thumbnail_url", "org_main_image_url", "description", "published_year", "min_players", "max_players", "playing_time", "min_play_time", "max_play_time", "min_age", "insert_date", "last_update")

  def apply(bgm: SyntaxProvider[BoardGameMst])(rs: WrappedResultSet): BoardGameMst = apply(bgm.resultName)(rs)
  def apply(bgm: ResultName[BoardGameMst])(rs: WrappedResultSet): BoardGameMst = new BoardGameMst(
    id = rs.get(bgm.id),
    parentId = rs.get(bgm.parentId),
    name = rs.get(bgm.name),
    orgThumbnailUrl = rs.get(bgm.orgThumbnailUrl),
    orgMainImageUrl = rs.get(bgm.orgMainImageUrl),
    description = rs.get(bgm.description),
    publishedYear = rs.get(bgm.publishedYear),
    minPlayers = rs.get(bgm.minPlayers),
    maxPlayers = rs.get(bgm.maxPlayers),
    playingTime = rs.get(bgm.playingTime),
    minPlayTime = rs.get(bgm.minPlayTime),
    maxPlayTime = rs.get(bgm.maxPlayTime),
    minAge = rs.get(bgm.minAge),
    insertDate = rs.get(bgm.insertDate),
    lastUpdate = rs.get(bgm.lastUpdate)
  )

  val bgm = BoardGameMst.syntax("bgm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BoardGameMst] = {
    withSQL {
      select.from(BoardGameMst as bgm).where.eq(bgm.id, id)
    }.map(BoardGameMst(bgm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BoardGameMst] = {
    withSQL(select.from(BoardGameMst as bgm)).map(BoardGameMst(bgm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BoardGameMst as bgm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BoardGameMst] = {
    withSQL {
      select.from(BoardGameMst as bgm).where.append(where)
    }.map(BoardGameMst(bgm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BoardGameMst] = {
    withSQL {
      select.from(BoardGameMst as bgm).where.append(where)
    }.map(BoardGameMst(bgm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BoardGameMst as bgm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    parentId: Option[Int] = None,
    name: String,
    orgThumbnailUrl: Option[String] = None,
    orgMainImageUrl: Option[String] = None,
    description: Option[String] = None,
    publishedYear: Option[LocalDate] = None,
    minPlayers: Option[Int] = None,
    maxPlayers: Option[Int] = None,
    playingTime: Option[Int] = None,
    minPlayTime: Option[Int] = None,
    maxPlayTime: Option[Int] = None,
    minAge: Option[Int] = None,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): BoardGameMst = {
    withSQL {
      insert.into(BoardGameMst).namedValues(
        column.id -> id,
        column.parentId -> parentId,
        column.name -> name,
        column.orgThumbnailUrl -> orgThumbnailUrl,
        column.orgMainImageUrl -> orgMainImageUrl,
        column.description -> description,
        column.publishedYear -> publishedYear,
        column.minPlayers -> minPlayers,
        column.maxPlayers -> maxPlayers,
        column.playingTime -> playingTime,
        column.minPlayTime -> minPlayTime,
        column.maxPlayTime -> maxPlayTime,
        column.minAge -> minAge,
        column.insertDate -> insertDate,
        column.lastUpdate -> lastUpdate
      )
    }.update.apply()

    BoardGameMst(
      id = id,
      parentId = parentId,
      name = name,
      orgThumbnailUrl = orgThumbnailUrl,
      orgMainImageUrl = orgMainImageUrl,
      description = description,
      publishedYear = publishedYear,
      minPlayers = minPlayers,
      maxPlayers = maxPlayers,
      playingTime = playingTime,
      minPlayTime = minPlayTime,
      maxPlayTime = maxPlayTime,
      minAge = minAge,
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[BoardGameMst])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'parentId -> entity.parentId,
        'name -> entity.name,
        'orgThumbnailUrl -> entity.orgThumbnailUrl,
        'orgMainImageUrl -> entity.orgMainImageUrl,
        'description -> entity.description,
        'publishedYear -> entity.publishedYear,
        'minPlayers -> entity.minPlayers,
        'maxPlayers -> entity.maxPlayers,
        'playingTime -> entity.playingTime,
        'minPlayTime -> entity.minPlayTime,
        'maxPlayTime -> entity.maxPlayTime,
        'minAge -> entity.minAge,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into board_game_mst(
      id,
      parent_id,
      name,
      org_thumbnail_url,
      org_main_image_url,
      description,
      published_year,
      min_players,
      max_players,
      playing_time,
      min_play_time,
      max_play_time,
      min_age,
      insert_date,
      last_update
    ) values (
      {id},
      {parentId},
      {name},
      {orgThumbnailUrl},
      {orgMainImageUrl},
      {description},
      {publishedYear},
      {minPlayers},
      {maxPlayers},
      {playingTime},
      {minPlayTime},
      {maxPlayTime},
      {minAge},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BoardGameMst)(implicit session: DBSession = autoSession): BoardGameMst = {
    withSQL {
      update(BoardGameMst).set(
        column.id -> entity.id,
        column.parentId -> entity.parentId,
        column.name -> entity.name,
        column.orgThumbnailUrl -> entity.orgThumbnailUrl,
        column.orgMainImageUrl -> entity.orgMainImageUrl,
        column.description -> entity.description,
        column.publishedYear -> entity.publishedYear,
        column.minPlayers -> entity.minPlayers,
        column.maxPlayers -> entity.maxPlayers,
        column.playingTime -> entity.playingTime,
        column.minPlayTime -> entity.minPlayTime,
        column.maxPlayTime -> entity.maxPlayTime,
        column.minAge -> entity.minAge,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BoardGameMst)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BoardGameMst).where.eq(column.id, entity.id) }.update.apply()
  }

}
