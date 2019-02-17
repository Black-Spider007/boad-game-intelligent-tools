package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class StaticInfo(
  id: Int,
  gameId: Int,
  usersRated: Option[Int] = None,
  averageRate: Option[Double] = None,
  bayesAverageRate: Option[Double] = None,
  stdDeviation: Option[Double] = None,
  median: Option[Int] = None,
  owned: Option[Int] = None,
  trading: Option[Int] = None,
  wanting: Option[Int] = None,
  wishing: Option[Int] = None,
  numOfComments: Option[Int] = None,
  numOfWeights: Option[Int] = None,
  averageWeight: Option[Double] = None,
  insertDate: Option[ZonedDateTime] = None,
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = StaticInfo.autoSession): StaticInfo = StaticInfo.save(this)(session)

  def destroy()(implicit session: DBSession = StaticInfo.autoSession): Int = StaticInfo.destroy(this)(session)

}


object StaticInfo extends SQLSyntaxSupport[StaticInfo] {

  override val tableName = "static_info"

  override val columns = Seq("id", "game_id", "users_rated", "average_rate", "bayes_average_rate", "std_deviation", "median", "owned", "trading", "wanting", "wishing", "num_of_comments", "num_of_weights", "average_weight", "insert_date", "last_update")

  def apply(si: SyntaxProvider[StaticInfo])(rs: WrappedResultSet): StaticInfo = apply(si.resultName)(rs)
  def apply(si: ResultName[StaticInfo])(rs: WrappedResultSet): StaticInfo = new StaticInfo(
    id = rs.get(si.id),
    gameId = rs.get(si.gameId),
    usersRated = rs.get(si.usersRated),
    averageRate = rs.get(si.averageRate),
    bayesAverageRate = rs.get(si.bayesAverageRate),
    stdDeviation = rs.get(si.stdDeviation),
    median = rs.get(si.median),
    owned = rs.get(si.owned),
    trading = rs.get(si.trading),
    wanting = rs.get(si.wanting),
    wishing = rs.get(si.wishing),
    numOfComments = rs.get(si.numOfComments),
    numOfWeights = rs.get(si.numOfWeights),
    averageWeight = rs.get(si.averageWeight),
    insertDate = rs.get(si.insertDate),
    lastUpdate = rs.get(si.lastUpdate)
  )

  val si = StaticInfo.syntax("si")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[StaticInfo] = {
    withSQL {
      select.from(StaticInfo as si).where.eq(si.id, id)
    }.map(StaticInfo(si.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[StaticInfo] = {
    withSQL(select.from(StaticInfo as si)).map(StaticInfo(si.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(StaticInfo as si)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[StaticInfo] = {
    withSQL {
      select.from(StaticInfo as si).where.append(where)
    }.map(StaticInfo(si.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[StaticInfo] = {
    withSQL {
      select.from(StaticInfo as si).where.append(where)
    }.map(StaticInfo(si.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(StaticInfo as si).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gameId: Int,
    usersRated: Option[Int] = None,
    averageRate: Option[Double] = None,
    bayesAverageRate: Option[Double] = None,
    stdDeviation: Option[Double] = None,
    median: Option[Int] = None,
    owned: Option[Int] = None,
    trading: Option[Int] = None,
    wanting: Option[Int] = None,
    wishing: Option[Int] = None,
    numOfComments: Option[Int] = None,
    numOfWeights: Option[Int] = None,
    averageWeight: Option[Double] = None,
    insertDate: Option[ZonedDateTime] = None,
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): StaticInfo = {
    val generatedKey = withSQL {
      insert.into(StaticInfo).namedValues(
        column.gameId -> gameId,
        column.usersRated -> usersRated,
        column.averageRate -> averageRate,
        column.bayesAverageRate -> bayesAverageRate,
        column.stdDeviation -> stdDeviation,
        column.median -> median,
        column.owned -> owned,
        column.trading -> trading,
        column.wanting -> wanting,
        column.wishing -> wishing,
        column.numOfComments -> numOfComments,
        column.numOfWeights -> numOfWeights,
        column.averageWeight -> averageWeight,
        column.insertDate -> insertDate,
        column.lastUpdate -> lastUpdate
      )
    }.updateAndReturnGeneratedKey.apply()

    StaticInfo(
      id = generatedKey.toInt,
      gameId = gameId,
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
      insertDate = insertDate,
      lastUpdate = lastUpdate)
  }

  def batchInsert(entities: collection.Seq[StaticInfo])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gameId -> entity.gameId,
        'usersRated -> entity.usersRated,
        'averageRate -> entity.averageRate,
        'bayesAverageRate -> entity.bayesAverageRate,
        'stdDeviation -> entity.stdDeviation,
        'median -> entity.median,
        'owned -> entity.owned,
        'trading -> entity.trading,
        'wanting -> entity.wanting,
        'wishing -> entity.wishing,
        'numOfComments -> entity.numOfComments,
        'numOfWeights -> entity.numOfWeights,
        'averageWeight -> entity.averageWeight,
        'insertDate -> entity.insertDate,
        'lastUpdate -> entity.lastUpdate))
    SQL("""insert into static_info(
      game_id,
      users_rated,
      average_rate,
      bayes_average_rate,
      std_deviation,
      median,
      owned,
      trading,
      wanting,
      wishing,
      num_of_comments,
      num_of_weights,
      average_weight,
      insert_date,
      last_update
    ) values (
      {gameId},
      {usersRated},
      {averageRate},
      {bayesAverageRate},
      {stdDeviation},
      {median},
      {owned},
      {trading},
      {wanting},
      {wishing},
      {numOfComments},
      {numOfWeights},
      {averageWeight},
      {insertDate},
      {lastUpdate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: StaticInfo)(implicit session: DBSession = autoSession): StaticInfo = {
    withSQL {
      update(StaticInfo).set(
        column.id -> entity.id,
        column.gameId -> entity.gameId,
        column.usersRated -> entity.usersRated,
        column.averageRate -> entity.averageRate,
        column.bayesAverageRate -> entity.bayesAverageRate,
        column.stdDeviation -> entity.stdDeviation,
        column.median -> entity.median,
        column.owned -> entity.owned,
        column.trading -> entity.trading,
        column.wanting -> entity.wanting,
        column.wishing -> entity.wishing,
        column.numOfComments -> entity.numOfComments,
        column.numOfWeights -> entity.numOfWeights,
        column.averageWeight -> entity.averageWeight,
        column.insertDate -> entity.insertDate,
        column.lastUpdate -> entity.lastUpdate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: StaticInfo)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(StaticInfo).where.eq(column.id, entity.id) }.update.apply()
  }

}
