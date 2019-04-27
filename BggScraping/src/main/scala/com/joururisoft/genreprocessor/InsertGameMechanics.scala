package com.joururisoft.genreprocessor

import com.joururisoft.models.GameMechanicMst

object InsertGameMechanics extends GameGenre {
  override protected def insertData(nameEn: String,
                                    nameJp: String,
                                    descriptionEn: String,
                                    descriptionJp: String,
                                    targetId: Int
                                   ): Unit = {
    val now = createZonedNow()
    GameMechanicMst.create(
      id = targetId,
      nameEn = Some(nameEn),
      nameJp = Some(nameJp),
      descriptionEn = Some(descriptionEn),
      descriptionJp = Some(descriptionJp),
      insertDate = Some(now),
      lastUpdate = Some(now)
    )
  }

  override protected def targetLink(): String = "/wiki/page/JP_BGG_Mechanics"

  override protected def truncateMst(): Unit = GameMechanicMst.truncate()
}
