import org.scalatest.funsuite.AnyFunSuite

class DataLoaderTest extends AnyFunSuite {

  test("load data - empty") {
    val empty = this.getClass.getClassLoader.getResource("empty.csv").getFile
    val data = DataLoader.load(empty)
    assert(data.headers.size == 2)
    assert(data.headers(0).equals("NAME"))
    assert(data.headers(1).equals("EXCLUDE"))
  }
  
}
