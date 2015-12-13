package com.gp.test.task2

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import akka.actor.ActorSystem

object CsvReaderActor extends App {
  
  val inputArgs = args
  
  if(args.length <2){
    println("Missing Arguments")
    println("Usage: CsvReaderActor <inputFile> <OutputFile>")
    sys.exit(1)
  }
  
  val system = ActorSystem("CsvReaderActor")
  val log = Logging.getLogger(system, this)
  
  val actor = system.actorOf(Props(new CsvReaderFileActor(inputArgs(0), inputArgs(1))))
  
  log.info("Started")
  actor ! startProcessing()
  
  system.shutdown()
}