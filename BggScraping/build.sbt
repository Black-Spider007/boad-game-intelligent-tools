name := "BggScraping"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
  "org.scalikejdbc" %% "scalikejdbc"       % "3.3.0",
  "com.h2database"  %  "h2"                % "1.4.197",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.codeborne" % "phantomjsdriver" % "1.4.4",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59"
)