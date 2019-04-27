package com.joururisoft

import scalikejdbc.ConnectionPool

trait BGAConnection {
  val BGA_DB_URL = "jdbc:mysql://localhost/board_game_analytics?characterEncoding=UTF-8&serverTimezone=JST"
  val BGA_DB_USER = "root"
  val BGA_DB_PASSWORD = "root"

  ConnectionPool.singleton(BGA_DB_URL, BGA_DB_USER, BGA_DB_PASSWORD)

}
