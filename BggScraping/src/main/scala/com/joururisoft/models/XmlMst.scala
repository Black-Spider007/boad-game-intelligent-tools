package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class XmlMst(
  id: Int,
  gameId: Int,
  xmlType: String,
  page: Int,
  xmlMst: Option[String] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = XmlMst.autoSession): XmlMst = XmlMst.save(this)(session)

  def destroy()(implicit session: DBSession = XmlMst.autoSession): Int = XmlMst.destroy(this)(session)

}


object XmlMst extends SQLSyntaxSupport[XmlMst] {

  override val tableName = "xml_mst"

  override val columns = Seq("id", "game_id", "xml_type", "page", "xml_mst", "insert_date", "last_update")

  def apply(xm: SyntaxProvider[XmlMst])(rs: WrappedResultSet): XmlMst = apply(xm.resultName)(rs)
  def apply(xm: ResultName[XmlMst])(rs: WrappedResultSet): XmlMst = new XmlMst(
    id = rs.get(xm.id),
    gameId = rs.get(xm.gameId),
    xmlType = rs.get(xm.xmlType),
    page = rs.get(xm.page),
    xmlMst = rs.get(xm.xmlMst),
    insertDate = rs.get(xm.insertDate),
    lastUpdate = rs.get(xm.lastUpdate)
  )

  val xm = XmlMst.syntax("xm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[XmlMst] = {
    withSQL {
      select.from(XmlMst as xm).where.eq(xm.id, id)
    }.map(XmlMst(xm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[XmlMst] = {
    withSQL(select.from(XmlMst as xm)).map(XmlMst(xm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(XmlMst as xm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[XmlMst] = {
    withSQL {
      select.from(XmlMst as xm).where.append(where)
    }.map(XmlMst(xm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[XmlMst] = {
    withSQL {
      select.from(XmlMst as xm).where.append(where)
    }.map(XmlMst(xm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(XmlMst as xm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gameId: Int,
    xmlType: String,
    page: Int,
    xmlMst: Option[String] = None,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): XmlMst = {
    val generatedKey = withSQL {
      insert.into(XmlMst).namedValues(
        column.gameId -> gameId,
        column.xmlType -> xmlType,
        column.page -> page,
        column.xmlMst -> xmlMst
      )
    }.updateAndReturnGeneratedKey.apply()

    XmlMst(
      id = generatedKey.toInt,
      gameId = gameId,
      xmlType = xmlType,
      page = page,
      xmlMst = xmlMst,
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[XmlMst])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gameId -> entity.gameId,
        'xmlType -> entity.xmlType,
        'page -> entity.page,
        'xmlMst -> entity.xmlMst,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into xml_mst(
      game_id,
      xml_type,
      page,
      xml_mst,
      insert_date,
      last_update
    ) values (
      {gameId},
      {xmlType},
      {page},
      {xmlMst},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: XmlMst)(implicit session: DBSession = autoSession): XmlMst = {
    withSQL {
      update(XmlMst).set(
        column.id -> entity.id,
        column.gameId -> entity.gameId,
        column.xmlType -> entity.xmlType,
        column.page -> entity.page,
        column.xmlMst -> entity.xmlMst,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: XmlMst)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(XmlMst).where.eq(column.id, entity.id) }.update.apply()
  }

}
