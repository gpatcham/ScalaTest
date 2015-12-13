package com.gp.test.task3

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object CsvReaderSpark extends App {
  
     val inputParams = args
     
     if(args.length <2){
    println("Missing Arguments")
    println("Usage: CsvReaderSpark <inputFile> <OutputFile>")
    sys.exit(1)
    }
     
     val inputFile = args(0)
     val outPutFile = args(1)
  
     val conf = new SparkConf().setAppName("CsvReader Spark")  
     val sc = new SparkContext(conf)
     
     val readFile = sc.textFile(inputFile)
     val header = readFile.first() //Skip header
     val csvData = readFile.filter(line => line != header)
     
     
     val tokenCntRdd = csvData.flatMap(line => line.split(",")).map(line => (line,1))
     
     val tokenCnt = tokenCntRdd.reduceByKey((a,b) => a+b).map(line=>line._1+","+line._2)
     val headerSave = sc.parallelize(Array("token,number_of_occurences"))
     
     headerSave.union(tokenCnt).coalesce(1).saveAsTextFile(outPutFile)
     
}