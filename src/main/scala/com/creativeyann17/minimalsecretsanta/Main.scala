package com.creativeyann17.minimalsecretsanta

import picocli.CommandLine
import picocli.CommandLine.{Command, Option}

import java.util.Optional

@Command(name = "minimal-secret-santa", mixinStandardHelpOptions = true,
  version = Array("0.1"),  
  description = Array("Scala implementation of a command line secret-santa."))
class Main extends Runnable {

  @Option(names = Array("-i", "--input"), description = Array("input CSV file with list of people (required)"), required = true)
  var input: String = null;

  @Option(names = Array("-t", "--template"), description = Array("template location to generate invitation messages (optional)"))
  var template: Optional[String] = Optional.empty
  
  override def run(): Unit = {
    val secretSanta = new SecretSanta(input)
    val results = secretSanta.go()
    println(results.mkString("\n"))
    if (template.isPresent) {
      results.foreach(r => {
        println("-" * 50)
        println(secretSanta.formatTemplate(template.get(), r._1, r._2))
      })
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val commandLine = new CommandLine(new Main)
    if (args.nonEmpty) {
      System.exit(commandLine.execute(args: _*))
    } else { // if no params then display usage
      commandLine.usage(System.out)
    }
  }
}
