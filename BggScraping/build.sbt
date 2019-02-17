name := "BggScraping"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.3.2" % "test",
  "mysql" % "mysql-connector-java" % "8.0.15",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.codeborne" % "phantomjsdriver" % "1.4.4",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
)

enablePlugins(ScalikejdbcPlugin)
