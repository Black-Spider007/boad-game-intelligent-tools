package com.joururisoft

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.phantomjs.{PhantomJSDriver, PhantomJSDriverService}
import org.openqa.selenium.remote.DesiredCapabilities

object PhantomjsExample extends App with LazyLogging {

  val caps = new DesiredCapabilities
  caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Program Files\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe")
  val driver = new PhantomJSDriver(caps)

  driver.get("https://boardgamegeek.com/boardgame/174430/gloomhaven")
  val file = driver.getPageSource

  logger.info(file)

  driver.quit()
}
