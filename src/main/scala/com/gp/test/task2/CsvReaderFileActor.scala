package com.gp.test.task2


import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import scala.io.Source
import java.io.File
import java.io.PrintWriter
import akka.actor.ActorLogging

case class startProcessing()

class CsvReaderFileActor(inputFile: String, outputFile: String) extends Actor with ActorLogging {

   var token = List("")
   
   var totalLine = 0
   var linesProcessed = 0
  
  def receive = {
    case startProcessing() => {
      val source = Source.fromFile(inputFile)
      log.info("Read File")
      source.getLines.drop(1).foreach{ line =>
        context.actorOf(Props[CsvReaderCounter]) ! readLine(line)
        totalLine += 1
      }
      source.close
    }
    case tokenList(word) => {
      token = token ::: word
      linesProcessed += 1
      if (linesProcessed == totalLine) {
        //context.actorOf(Props[CsvReaderCounter]) ! tokenList(token)
        val tokenCt = token.groupBy(word => word).mapValues(line => line.length)
        writeFile(tokenCt)
        log.info("Completed!!")
      }
      
    }
    
    
    case _ => println("Wrong Request")
    
  }
  
  def writeFile(tokenCt: Map[String,Int]){
  val writer = new PrintWriter(new File(outputFile))
  
  writer.println("token,number_of_occurences")
  tokenCt foreach ( line => writer.println(line._1 + "," +line._2))
  writer.close
  }
}