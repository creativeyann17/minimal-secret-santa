package com.creativeyann17.minimalsecretsanta

import org.scalatest.funsuite.AnyFunSuite

class SecretSantaTest extends AnyFunSuite {

  test("secret santa - simple") {
    val simple = this.getClass.getClassLoader.getResource("simple.csv").getFile
    val results = new SecretSanta(simple).go()
    assert(results("a") != null)
    assert(results("b") != null)
    assert(results("c") != null)
  }

  test("secret santa - exclude") {
    val exclude = this.getClass.getClassLoader.getResource("exclude.csv").getFile
    val secretSanta = new SecretSanta(exclude)
    assert(secretSanta.namesWithExclude("a").equals(List("b")))
    assert(secretSanta.namesWithExclude("b").equals(List("a","c")))
  }

  test("secret santa - everyone has a target person and no duplicates") {
    val robustness = this.getClass.getClassLoader.getResource("robustness.csv").getFile
    val secretSanta = new SecretSanta(robustness)
    val results = secretSanta.go()
    val expected  = List("a","b","c","d","e","f","g","h","i","j")
    assert(results.size.equals(expected.size))
    expected.foreach(from => {
      val to = results(from)
      assert(to != null)
      val anyoneElse = results.filterNot(anyone => anyone._1.equals(from))
      // the following check that anybody else has this person
      anyoneElse.foreach(anyone => assert(!results(anyone._1).equals(to)))
    })
  }

  test("secret santa - no loop like a -> b and b -> a") {
    val simple = this.getClass.getClassLoader.getResource("simple.csv").getFile
    val secretSanta = new SecretSanta(simple)
    val results = secretSanta.go()
    results.foreach({case(from, to) =>
      assert(!results(to).equals(from))
    })
  }

  test("secret santa - format template") {
    val simple = this.getClass.getClassLoader.getResource("simple.csv").getFile
    val template = this.getClass.getClassLoader.getResource("template.md").getFile
    val secretSanta = new SecretSanta(simple)
    val results = secretSanta.go()
    val expectedTemplate = "a will give a gift to " + results("a")
    assert(secretSanta.formatTemplate(template, "a", results("a")).equals(expectedTemplate))
  }

  test("secret santa - retry + impossible to compute") {
    val impossible = this.getClass.getClassLoader.getResource("impossible.csv").getFile
    val secretSanta = new SecretSanta(impossible)
    val e = intercept[IllegalStateException] {
      secretSanta.go(10)
    }
    assert(e.getMessage.equals("No remaining possibilities"))
  }
}
