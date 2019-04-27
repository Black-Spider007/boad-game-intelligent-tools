package com.joururisoft

trait BGGScrapingHeader {
  val BASE_URL = "https://boardgamegeek.com"
  val userAgent = "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1"
  System.setProperty("phantomjs.page.settings.userAgent", userAgent)
}
