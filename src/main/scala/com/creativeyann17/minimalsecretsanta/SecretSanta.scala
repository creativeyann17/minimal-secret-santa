package com.creativeyann17.minimalsecretsanta

import scala.collection.mutable
import scala.util.Random

class SecretSanta(path: String)(implicit separator: Char = ',', excludeSeparator: Char = '|') {

  val data: DataLoader.Data = DataLoader.load(path)(separator)
  val namesWithExclude: Map[String, List[String]] = getNamesWithExclude(data)
  
  def go(retry: Int = 3): Map[String, String] = {
    
    val results = mutable.Map[String, String]()
    val available = mutable.ListBuffer().addAll(namesWithExclude.keys)
    val rand = new Random()
    
    namesWithExclude.foreach({case(name, exclude) =>
      // avoid people loop like a => b and b => a
      val thisPersonGaveToMe = findSomeoneWhoGaveTo(name, results.toMap).getOrElse("")
      // ignore the exclude list + self + the person who gave to me (if exists)
      val possibilities = available.filter(n => !exclude.contains(n) && !n.equals(name) && !n.equals(thisPersonGaveToMe))
      if (possibilities.isEmpty) {  // too much constraints on exclude
        if (retry > 0) {
          return go(retry-1)  // in that case retry, otherwise fail after too much retries
        } else {
          throw new IllegalStateException("No remaining possibilities")
        }
      }
      val to = possibilities(rand.nextInt(possibilities.size))
      results += (name -> to)
      available -= to
    })

    if (available.nonEmpty) { // we don't want that to happen
      throw new IllegalStateException(s"Remaining people without gift: $available")
    }
    
    results.toMap
  }
  
  def findSomeoneWhoGaveTo(to: String, results: Map[String, String]): Option[String] = {
    results.find({case(_,r) => r.equals(to)}).map(_._1)
  }
  
  def formatTemplate(template: String, from: String, to: String): String = {
    DataLoader.loadTemplate(template).replace("{FROM}", from).replace("{TO}", to)
  }
  
  private def getNamesWithExclude(data: DataLoader.Data): Map[String, List[String]] = {
    data.records.zipWithIndex.map({case(record, index) => (record.get(Name.header), getExcludeByIndex(data,index))}).toMap
  }

  private def getExcludeByIndex(data: DataLoader.Data, index: Int): List[String] = {
    if(data.records(index).isSet(Exclude.header))
      data.records(index).get(Exclude.header).split(excludeSeparator).toList
    else List()
  }
}
