package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class GameFamilyMst(
  id: Int,
  nameEn: Option[String] = None,
  nameJp: Option[String] = None,
  alternateNamesEn: Option[String] = None,
  alternateNamesJp: Option[String] = None,
  descriptionEn: Option[String] = None,
  descriptionJp: Option[String] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = GameFamilyMst.autoSession): GameFamilyMst = GameFamilyMst.save(this)(session)

  def destroy()(implicit session: DBSession = GameFamilyMst.autoSession): Int = GameFamilyMst.destroy(this)(session)

}


object GameFamilyMst extends SQLSyntaxSupport[GameFamilyMst] {

  override val tableName = "game_family_mst"

  override val columns = Seq("id", "name_en", "name_jp", "alternate_names_en", "alternate_names_jp", "description_en", "description_jp", "insert_date", "last_update")

  def apply(gfm: SyntaxProvider[GameFamilyMst])(rs: WrappedResultSet): GameFamilyMst = apply(gfm.resultName)(rs)
  def apply(gfm: ResultName[GameFamilyMst])(rs: WrappedResultSet): GameFamilyMst = new GameFamilyMst(
    id = rs.get(gfm.id),
    nameEn = rs.get(gfm.nameEn),
    nameJp = rs.get(gfm.nameJp),
    alternateNamesEn = rs.get(gfm.alternateNamesEn),
    alternateNamesJp = rs.get(gfm.alternateNamesJp),
    descriptionEn = rs.get(gfm.descriptionEn),
    descriptionJp = rs.get(gfm.descriptionJp),
    insertDate = rs.get(gfm.insertDate),
    lastUpdate = rs.get(gfm.lastUpdate)
  )

  val gfm = GameFamilyMst.syntax("gfm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GameFamilyMst] = {
    withSQL {
      select.from(GameFamilyMst as gfm).where.eq(gfm.id, id)
    }.map(GameFamilyMst(gfm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GameFamilyMst] = {
    withSQL(select.from(GameFamilyMst as gfm)).map(GameFamilyMst(gfm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GameFamilyMst as gfm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GameFamilyMst] = {
    withSQL {
      select.from(GameFamilyMst as gfm).where.append(where)
    }.map(GameFamilyMst(gfm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GameFamilyMst] = {
    withSQL {
      select.from(GameFamilyMst as gfm).where.append(where)
    }.map(GameFamilyMst(gfm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GameFamilyMst as gfm).where.append(where)
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
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): GameFamilyMst = {
    withSQL {
      insert.into(GameFamilyMst).namedValues(
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

    GameFamilyMst(
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

  def batchInsert(entities: collection.Seq[GameFamilyMst])(implicit session: DBSession = autoSession): List[Int] = {
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
    SQL("""insert into game_family_mst(
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

  def save(entity: GameFamilyMst)(implicit session: DBSession = autoSession): GameFamilyMst = {
    withSQL {
      update(GameFamilyMst).set(
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

  def destroy(entity: GameFamilyMst)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GameFamilyMst).where.eq(column.id, entity.id) }.update.apply()
  }

}
