import org.apache.commons.csv.{CSVFormat, CSVRecord}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters.CollectionHasAsScala

object DataLoader {
  
  case class Data(headers: List[String], records: List[CSVRecord])

  def load(path: String)(implicit separator: Char = ','): Data = {
    val reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)
    val parser = CSVFormat.DEFAULT
      .withDelimiter(separator)
      .withFirstRecordAsHeader()
      .withIgnoreEmptyLines()
      .withTrim()
      .parse(reader)

    val records = parser.getRecords.asScala.toList
    val headers = parser.getHeaderNames.asScala.toList

    reader.close()

    Data(headers, records)
  }

}
