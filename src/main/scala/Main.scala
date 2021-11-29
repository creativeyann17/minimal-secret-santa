object Main {
  def main(args: Array[String]): Unit = {
    val secretSanta = new SecretSanta(args(0))
    println(secretSanta.results)
  }
}
