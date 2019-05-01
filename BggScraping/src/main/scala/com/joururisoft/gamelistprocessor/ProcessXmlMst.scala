package com.joururisoft.gamelistprocessor

import com.joururisoft.BGAConnection
import com.joururisoft.models.XmlMst

import scala.xml.XML

object ProcessXmlMst extends App with BGAConnection {
  run()

  def run(): Unit = {
    val xmlMstList = XmlMst.findAll()

    xmlMstList.foreach { xmlMstRecord =>
      val xmlMst = XML.loadString(xmlMstRecord.xmlMst.getOrElse(""))
      XmlToDB.insertGameMst(xmlMst)
      XmlToDB.insertStaticInfo(xmlMst)
      XmlToDB.insertMechanicsRelation(xmlMst)
      XmlToDB.insertCategoryRelation(xmlMst)
    }
  }
}
