package com.joururisoft

trait BGGScrapingHeader {
  val BASE_URL = "https://boardgamegeek.com"
  val userAgent = "My service URL: https://joururi-soft.com /n ID: DilemmaHoldem, Password: texasholdem"
  System.setProperty("phantomjs.page.settings.userAgent", userAgent)
}
