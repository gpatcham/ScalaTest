package com.gp.test.task1

import java.io.File
import scala.io.Source
import java.io.{FileNotFoundException, IOException}
import java.io.PrintWriter

object CsvReader extends App {
  
  val inputArgs = args
  
  if(args.length <2){
    println("Missing Arguments")
    println("Usage: CsvReader <inputFile> <OutputFile>")
    sys.exit(1)
  }
  
  val inputFile = inputArgs(0)
  val outputFile = inputArgs(1)
  
  try{
    
  //read file
  val source = Source.fromFile(inputFile)
  
  
  val tokenMapCount = source.
                     getLines().
                     drop(1).         
                     flatMap(line => line.split(",")).
                     toList.
                     groupBy(word => word).
                     mapValues(line => line.length)
                     
  val writer = new PrintWriter(new File(outputFile))
  
  writer.println("token,number_of_occurences")
  tokenMapCount foreach ( line => writer.println(line._1 + "," +line._2))
  
  source.close()
  writer.close()
    
  }
  catch {
    case e: FileNotFoundException => println("File Not Found")
    case e: IOException => println("IO exception")
  }
   
  
}