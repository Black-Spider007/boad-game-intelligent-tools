package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class GameCategoryMst(
  id: Int,
  nameEn: Option[String] = None,
  nameJp: Option[String] = None,
  alternateNamesEn: Option[String] = None,
  alternateNamesJp: Option[String] = None,
  descriptionEn: Option[String] = None,
  descriptionJp: Option[String] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = GameCategoryMst.autoSession): GameCategoryMst = GameCategoryMst.save(this)(session)

  def destroy()(implicit session: DBSession = GameCategoryMst.autoSession): Int = GameCategoryMst.destroy(this)(session)

}


object GameCategoryMst extends SQLSyntaxSupport[GameCategoryMst] {

  override val tableName = "game_category_mst"

  override val columns = Seq("id", "name_en", "name_jp", "alternate_names_en", "alternate_names_jp", "description_en", "description_jp", "insert_date", "last_update")

  def apply(gcm: SyntaxProvider[GameCategoryMst])(rs: WrappedResultSet): GameCategoryMst = apply(gcm.resultName)(rs)
  def apply(gcm: ResultName[GameCategoryMst])(rs: WrappedResultSet): GameCategoryMst = new GameCategoryMst(
    id = rs.get(gcm.id),
    nameEn = rs.get(gcm.nameEn),
    nameJp = rs.get(gcm.nameJp),
    alternateNamesEn = rs.get(gcm.alternateNamesEn),
    alternateNamesJp = rs.get(gcm.alternateNamesJp),
    descriptionEn = rs.get(gcm.descriptionEn),
    descriptionJp = rs.get(gcm.descriptionJp),
    insertDate = rs.get(gcm.insertDate),
    lastUpdate = rs.get(gcm.lastUpdate)
  )

  val gcm = GameCategoryMst.syntax("gcm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GameCategoryMst] = {
    withSQL {
      select.from(GameCategoryMst as gcm).where.eq(gcm.id, id)
    }.map(GameCategoryMst(gcm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GameCategoryMst] = {
    withSQL(select.from(GameCategoryMst as gcm)).map(GameCategoryMst(gcm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GameCategoryMst as gcm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GameCategoryMst] = {
    withSQL {
      select.from(GameCategoryMst as gcm).where.append(where)
    }.map(GameCategoryMst(gcm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GameCategoryMst] = {
    withSQL {
      select.from(GameCategoryMst as gcm).where.append(where)
    }.map(GameCategoryMst(gcm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GameCategoryMst as gcm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    nameEn: Option[String] = None,
    nameJp: Option[String] = None,
    alternateNamesEn: Option[String] = None,
    alternateNamesJp: Option[String] = None,
    descriptionEn: Option[String] = None,
    descriptionJp: Option[String] = None,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): GameCategoryMst = {
    withSQL {
      insert.into(GameCategoryMst).namedValues(
        column.id -> id,
        column.nameEn -> nameEn,
        column.nameJp -> nameJp,
        column.alternateNamesEn -> alternateNamesEn,
        column.alternateNamesJp -> alternateNamesJp,
        column.descriptionEn -> descriptionEn,
        column.descriptionJp -> descriptionJp,
        column.insertDate -> insertDate,
        column.lastUpdate -> lastUpdate
      )
    }.update.apply()

    GameCategoryMst(
      id = id,
      nameEn = nameEn,
      nameJp = nameJp,
      alternateNamesEn = alternateNamesEn,
      alternateNamesJp = alternateNamesJp,
      descriptionEn = descriptionEn,
      descriptionJp = descriptionJp,
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[GameCategoryMst])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'nameEn -> entity.nameEn,
        'nameJp -> entity.nameJp,
        'alternateNamesEn -> entity.alternateNamesEn,
        'alternateNamesJp -> entity.alternateNamesJp,
        'descriptionEn -> entity.descriptionEn,
        'descriptionJp -> entity.descriptionJp,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into game_category_mst(
      id,
      name_en,
      name_jp,
      alternate_names_en,
      alternate_names_jp,
      description_en,
      description_jp,
      insert_date,
      last_update
    ) values (
      {id},
      {nameEn},
      {nameJp},
      {alternateNamesEn},
      {alternateNamesJp},
      {descriptionEn},
      {descriptionJp},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: GameCategoryMst)(implicit session: DBSession = autoSession): GameCategoryMst = {
    withSQL {
      update(GameCategoryMst).set(
        column.id -> entity.id,
        column.nameEn -> entity.nameEn,
        column.nameJp -> entity.nameJp,
        column.alternateNamesEn -> entity.alternateNamesEn,
        column.alternateNamesJp -> entity.alternateNamesJp,
        column.descriptionEn -> entity.descriptionEn,
        column.descriptionJp -> entity.descriptionJp,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: GameCategoryMst)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GameCategoryMst).where.eq(column.id, entity.id) }.update.apply()
  }

}
