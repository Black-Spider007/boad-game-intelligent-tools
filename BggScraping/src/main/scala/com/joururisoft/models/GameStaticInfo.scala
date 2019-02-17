package com.joururisoft.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class GameStaticInfo(
  id: Int,
  gameId: Int,
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
  lastUpdate: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = GameStaticInfo.autoSession): GameStaticInfo = GameStaticInfo.save(this)(session)

  def destroy()(implicit session: DBSession = GameStaticInfo.autoSession): Int = GameStaticInfo.destroy(this)(session)

}


object GameStaticInfo extends SQLSyntaxSupport[GameStaticInfo] {

  override val tableName = "game_static_info"

  override val columns = Seq("id", "game_id", "users_rated", "average_rate", "bayes_average_rate", "std_eviation", "median", "owned", "trading", "wanting", "wishing", "num_of_comments", "num_of_weights", "average_weight", "insert_date", "last_update")

  def apply(gsi: SyntaxProvider[GameStaticInfo])(rs: WrappedResultSet): GameStaticInfo = apply(gsi.resultName)(rs)
  def apply(gsi: ResultName[GameStaticInfo])(rs: WrappedResultSet): GameStaticInfo = new GameStaticInfo(
    id = rs.get(gsi.id),
    gameId = rs.get(gsi.gameId),
    usersRated = rs.get(gsi.usersRated),
    averageRate = rs.get(gsi.averageRate),
    bayesAverageRate = rs.get(gsi.bayesAverageRate),
    stdEviation = rs.get(gsi.stdEviation),
    median = rs.get(gsi.median),
    owned = rs.get(gsi.owned),
    trading = rs.get(gsi.trading),
    wanting = rs.get(gsi.wanting),
    wishing = rs.get(gsi.wishing),
    numOfComments = rs.get(gsi.numOfComments),
    numOfWeights = rs.get(gsi.numOfWeights),
    averageWeight = rs.get(gsi.averageWeight),
    insertDate = rs.get(gsi.insertDate),
    lastUpdate = rs.get(gsi.lastUpdate)
  )

  val gsi = GameStaticInfo.syntax("gsi")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GameStaticInfo] = {
    withSQL {
      select.from(GameStaticInfo as gsi).where.eq(gsi.id, id)
    }.map(GameStaticInfo(gsi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GameStaticInfo] = {
    withSQL(select.from(GameStaticInfo as gsi)).map(GameStaticInfo(gsi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GameStaticInfo as gsi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GameStaticInfo] = {
    withSQL {
      select.from(GameStaticInfo as gsi).where.append(where)
    }.map(GameStaticInfo(gsi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GameStaticInfo] = {
    withSQL {
      select.from(GameStaticInfo as gsi).where.append(where)
    }.map(GameStaticInfo(gsi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GameStaticInfo as gsi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gameId: Int,
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
    lastUpdate: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): GameStaticInfo = {
    val generatedKey = withSQL {
      insert.into(GameStaticInfo).namedValues(
        column.gameId -> gameId,
        column.usersRated -> usersRated,
        column.averageRate -> averageRate,
        column.bayesAverageRate -> bayesAverageRate,
        column.stdEviation -> stdEviation,
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

    GameStaticInfo(
      id = generatedKey.toInt,
      gameId = gameId,
      usersRated = usersRated,
      averageRate = averageRate,
      bayesAverageRate = bayesAverageRate,
      stdEviation = stdEviation,
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

  def batchInsert(entities: collection.Seq[GameStaticInfo])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gameId -> entity.gameId,
        'usersRated -> entity.usersRated,
        'averageRate -> entity.averageRate,
        'bayesAverageRate -> entity.bayesAverageRate,
        'stdEviation -> entity.stdEviation,
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
    SQL("""insert into game_static_info(
      game_id,
      users_rated,
      average_rate,
      bayes_average_rate,
      std_eviation,
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
      {stdEviation},
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

  def save(entity: GameStaticInfo)(implicit session: DBSession = autoSession): GameStaticInfo = {
    withSQL {
      update(GameStaticInfo).set(
        column.id -> entity.id,
        column.gameId -> entity.gameId,
        column.usersRated -> entity.usersRated,
        column.averageRate -> entity.averageRate,
        column.bayesAverageRate -> entity.bayesAverageRate,
        column.stdEviation -> entity.stdEviation,
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

  def destroy(entity: GameStaticInfo)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GameStaticInfo).where.eq(column.id, entity.id) }.update.apply()
  }

}
