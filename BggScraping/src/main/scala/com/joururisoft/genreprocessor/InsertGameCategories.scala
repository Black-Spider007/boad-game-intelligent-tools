package com.joururisoft.genreprocessor

import com.joururisoft.models.GameCategoryMst

object InsertGameCategories extends GameGenre {
  override protected def insertData(nameEn: String,
                                    nameJp: String,
                                    descriptionEn: String,
                                    descriptionJp: String,
                                    targetId: Int
                                   ): Unit = {
    val now = createZonedNow()
    GameCategoryMst.create(
      id = targetId,
      nameEn = Some(nameEn),
      nameJp = Some(nameJp),
      descriptionEn = Some(descriptionEn),
      descriptionJp = Some(descriptionJp),
      insertDate = Some(now),
      lastUpdate = Some(now)
    )
  }

  override protected def targetLink(): String = "/wiki/page/JP_BGG_Categories"

  override protected def truncateMst(): Unit = GameCategoryMst.truncate()
}
