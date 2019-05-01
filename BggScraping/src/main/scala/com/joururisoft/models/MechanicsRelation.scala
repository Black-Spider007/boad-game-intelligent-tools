package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class MechanicsRelation(
  id: Int,
  gameId: Int,
  mechanicsId: Int,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = MechanicsRelation.autoSession): MechanicsRelation = MechanicsRelation.save(this)(session)

  def destroy()(implicit session: DBSession = MechanicsRelation.autoSession): Int = MechanicsRelation.destroy(this)(session)

}


object MechanicsRelation extends SQLSyntaxSupport[MechanicsRelation] {

  override val tableName = "mechanics_relation"

  override val columns = Seq("id", "game_id", "mechanics_id", "insert_date", "last_update")

  def apply(mr: SyntaxProvider[MechanicsRelation])(rs: WrappedResultSet): MechanicsRelation = apply(mr.resultName)(rs)
  def apply(mr: ResultName[MechanicsRelation])(rs: WrappedResultSet): MechanicsRelation = new MechanicsRelation(
    id = rs.get(mr.id),
    gameId = rs.get(mr.gameId),
    mechanicsId = rs.get(mr.mechanicsId),
    insertDate = rs.get(mr.insertDate),
    lastUpdate = rs.get(mr.lastUpdate)
  )

  val mr = MechanicsRelation.syntax("mr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MechanicsRelation] = {
    withSQL {
      select.from(MechanicsRelation as mr).where.eq(mr.id, id)
    }.map(MechanicsRelation(mr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MechanicsRelation] = {
    withSQL(select.from(MechanicsRelation as mr)).map(MechanicsRelation(mr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MechanicsRelation as mr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MechanicsRelation] = {
    withSQL {
      select.from(MechanicsRelation as mr).where.append(where)
    }.map(MechanicsRelation(mr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MechanicsRelation] = {
    withSQL {
      select.from(MechanicsRelation as mr).where.append(where)
    }.map(MechanicsRelation(mr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MechanicsRelation as mr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gameId: Int,
    mechanicsId: Int,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): MechanicsRelation = {
    val generatedKey = withSQL {
      insert.into(MechanicsRelation).namedValues(
        column.gameId -> gameId,
        column.mechanicsId -> mechanicsId,
        column.insertDate -> insertDate,
        column.lastUpdate -> lastUpdate
      )
    }.updateAndReturnGeneratedKey.apply()

    MechanicsRelation(
      id = generatedKey.toInt,
      gameId = gameId,
      mechanicsId = mechanicsId,
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[MechanicsRelation])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gameId -> entity.gameId,
        'mechanicsId -> entity.mechanicsId,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into mechanics_relation(
      game_id,
      mechanics_id,
      insert_date,
      last_update
    ) values (
      {gameId},
      {mechanicsId},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MechanicsRelation)(implicit session: DBSession = autoSession): MechanicsRelation = {
    withSQL {
      update(MechanicsRelation).set(
        column.id -> entity.id,
        column.gameId -> entity.gameId,
        column.mechanicsId -> entity.mechanicsId,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MechanicsRelation)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MechanicsRelation).where.eq(column.id, entity.id) }.update.apply()
  }

}
