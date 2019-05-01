package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class CategoryRelation(
  id: Int,
  gameId: Int,
  categoryId: Int,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = CategoryRelation.autoSession): CategoryRelation = CategoryRelation.save(this)(session)

  def destroy()(implicit session: DBSession = CategoryRelation.autoSession): Int = CategoryRelation.destroy(this)(session)

}


object CategoryRelation extends SQLSyntaxSupport[CategoryRelation] {

  override val tableName = "category_relation"

  override val columns = Seq("id", "game_id", "category_id", "insert_date", "last_update")

  def apply(cr: SyntaxProvider[CategoryRelation])(rs: WrappedResultSet): CategoryRelation = apply(cr.resultName)(rs)
  def apply(cr: ResultName[CategoryRelation])(rs: WrappedResultSet): CategoryRelation = new CategoryRelation(
    id = rs.get(cr.id),
    gameId = rs.get(cr.gameId),
    categoryId = rs.get(cr.categoryId),
    insertDate = rs.get(cr.insertDate),
    lastUpdate = rs.get(cr.lastUpdate)
  )

  val cr = CategoryRelation.syntax("cr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CategoryRelation] = {
    withSQL {
      select.from(CategoryRelation as cr).where.eq(cr.id, id)
    }.map(CategoryRelation(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CategoryRelation] = {
    withSQL(select.from(CategoryRelation as cr)).map(CategoryRelation(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CategoryRelation as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CategoryRelation] = {
    withSQL {
      select.from(CategoryRelation as cr).where.append(where)
    }.map(CategoryRelation(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CategoryRelation] = {
    withSQL {
      select.from(CategoryRelation as cr).where.append(where)
    }.map(CategoryRelation(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CategoryRelation as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gameId: Int,
    categoryId: Int,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): CategoryRelation = {
    val generatedKey = withSQL {
      insert.into(CategoryRelation).namedValues(
        column.gameId -> gameId,
        column.categoryId -> categoryId,
        column.insertDate -> insertDate,
        column.lastUpdate -> lastUpdate
      )
    }.updateAndReturnGeneratedKey.apply()

    CategoryRelation(
      id = generatedKey.toInt,
      gameId = gameId,
      categoryId = categoryId,
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[CategoryRelation])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gameId -> entity.gameId,
        'categoryId -> entity.categoryId,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into category_relation(
      game_id,
      category_id,
      insert_date,
      last_update
    ) values (
      {gameId},
      {categoryId},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CategoryRelation)(implicit session: DBSession = autoSession): CategoryRelation = {
    withSQL {
      update(CategoryRelation).set(
        column.id -> entity.id,
        column.gameId -> entity.gameId,
        column.categoryId -> entity.categoryId,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CategoryRelation)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CategoryRelation).where.eq(column.id, entity.id) }.update.apply()
  }

}
