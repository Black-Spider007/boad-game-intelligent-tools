package com.joururisoft

import com.joururisoft.models.XmlMst
import com.joururisoft.utils.XmlToDB

import scala.xml.XML

object ProcessXmlMst extends BGAConnection {
  run()

  def run(): Unit = {
    val xmlMstList = XmlMst.findAll()

    xmlMstList.foreach { xmlMstRecord =>
      val xmlMst = XML.loadString(xmlMstRecord.xmlMst.getOrElse(""))
//      XmlToDB.insertGameMst(xmlMst)
      XmlToDB.insertStaticInfo(xmlMst)
    }
  }
}
