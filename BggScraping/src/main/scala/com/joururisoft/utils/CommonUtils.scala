package com.joururisoft.utils

import java.time.{ZoneId, ZonedDateTime}

trait CommonUtils {
  def createZonedNow(): ZonedDateTime = {
    ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
  }

}