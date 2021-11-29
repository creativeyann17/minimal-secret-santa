object Main {

  def main(args: Array[String]): Unit = {
    val secretSanta = new SecretSanta(args(0))
    val results = secretSanta.go();
    println(results)
    results.foreach(r => println(secretSanta.formatTemplate(args(1), r._1, r._2)))
  }
}
