import scala.collection.mutable
import scala.util.Random

class SecretSanta(path: String)(implicit separator: Char = ',', excludeSeparator: Char = '|') {

  val data: DataLoader.Data = DataLoader.load(path)(separator)
  val namesWithExclude: Map[String, List[String]] = getNamesWithExclude(data)
  
  def go(): Map[String, String] = {
    
    val results: mutable.Map[String, String] = mutable.Map()
    val available = mutable.ListBuffer().addAll(namesWithExclude.keys)
    val rand = new Random()
    
    namesWithExclude.foreach({case(name, exclude) =>
      // avoid people loop like a => b and b => a
      val niceToAvoid = results.find({case(_,r) => r.equals(name)}).map(_._1).getOrElse("")
      // ignore the exclude list + self + nice to avoid (if exists)
      val possibilities = available.filter(n => !exclude.contains(n) && !n.equals(name) && !n.equals(niceToAvoid))
      if (possibilities.isEmpty) {  // too much constraints on exclude
        throw new RuntimeException("No remaining possibilities")
      }
      val to = possibilities(rand.nextInt(possibilities.size))
      results += (name -> to)
      available -= to
    })

    if (available.nonEmpty) { // we don't want that to happen
      throw new RuntimeException(s"Remaining people without gift: $available")
    }
    
    results.toMap
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
