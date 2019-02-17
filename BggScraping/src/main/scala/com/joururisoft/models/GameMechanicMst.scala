package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class GameMechanicMst(
  id: Int,
  nameEn: Option[String] = None,
  nameJp: Option[String] = None,
  alternateNamesEn: Option[String] = None,
  alternateNamesJp: Option[String] = None,
  descriptionEn: Option[String] = None,
  descriptionJp: Option[String] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = GameMechanicMst.autoSession): GameMechanicMst = GameMechanicMst.save(this)(session)

  def destroy()(implicit session: DBSession = GameMechanicMst.autoSession): Int = GameMechanicMst.destroy(this)(session)

}


object GameMechanicMst extends SQLSyntaxSupport[GameMechanicMst] {

  override val tableName = "game_mechanic_mst"

  override val columns = Seq("id", "name_en", "name_jp", "alternate_names_en", "alternate_names_jp", "description_en", "description_jp", "insert_date", "last_update")

  def apply(gmm: SyntaxProvider[GameMechanicMst])(rs: WrappedResultSet): GameMechanicMst = apply(gmm.resultName)(rs)
  def apply(gmm: ResultName[GameMechanicMst])(rs: WrappedResultSet): GameMechanicMst = new GameMechanicMst(
    id = rs.get(gmm.id),
    nameEn = rs.get(gmm.nameEn),
    nameJp = rs.get(gmm.nameJp),
    alternateNamesEn = rs.get(gmm.alternateNamesEn),
    alternateNamesJp = rs.get(gmm.alternateNamesJp),
    descriptionEn = rs.get(gmm.descriptionEn),
    descriptionJp = rs.get(gmm.descriptionJp),
    insertDate = rs.get(gmm.insertDate),
    lastUpdate = rs.get(gmm.lastUpdate)
  )

  val gmm = GameMechanicMst.syntax("gmm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GameMechanicMst] = {
    withSQL {
      select.from(GameMechanicMst as gmm).where.eq(gmm.id, id)
    }.map(GameMechanicMst(gmm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GameMechanicMst] = {
    withSQL(select.from(GameMechanicMst as gmm)).map(GameMechanicMst(gmm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GameMechanicMst as gmm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GameMechanicMst] = {
    withSQL {
      select.from(GameMechanicMst as gmm).where.append(where)
    }.map(GameMechanicMst(gmm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GameMechanicMst] = {
    withSQL {
      select.from(GameMechanicMst as gmm).where.append(where)
    }.map(GameMechanicMst(gmm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GameMechanicMst as gmm).where.append(where)
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
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): GameMechanicMst = {
    withSQL {
      insert.into(GameMechanicMst).namedValues(
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

    GameMechanicMst(
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

  def batchInsert(entities: collection.Seq[GameMechanicMst])(implicit session: DBSession = autoSession): List[Int] = {
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
    SQL("""insert into game_mechanic_mst(
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

  def save(entity: GameMechanicMst)(implicit session: DBSession = autoSession): GameMechanicMst = {
    withSQL {
      update(GameMechanicMst).set(
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

  def destroy(entity: GameMechanicMst)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GameMechanicMst).where.eq(column.id, entity.id) }.update.apply()
  }

}
