package com.gp.test.task2

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

case class readLine(line : String)
case class tokenList(word: List[String])

class CsvReaderCounter extends Actor {
  
  def receive = {    
    case(readLine(line)) => {
      val splitLine = line.split(",").toList
      
      sender ! tokenList(splitLine)
    }
    
   
    case _ => println("Wrong Request")
    
  }

}