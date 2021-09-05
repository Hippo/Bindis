package rip.hippo.bindis.test


import org.scalatest.funsuite.AnyFunSuite
import rip.hippo.bindis.format.elf.ElfReader
import rip.hippo.bindis.format.elf.section.types.impl.RawSection

import scala.util.{Failure, Using}


/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final class BasicReadTest extends AnyFunSuite {

  private val fileName = "hello"

  test("ElfParser.test") {
    Option(Thread.currentThread().getContextClassLoader.getResourceAsStream(s"$fileName")) match {
      case Some(value) =>
        val test = Using(ElfReader(value)) {
          elfReader =>
            val header = elfReader.elf32Header.get.header
            val table = header.getSectionNameStringTable()
            header.sectionHeaders.foreach(section => {
              println(table.get(section.shName))
            })
        }
        test match {
          case Failure(exception) => throw exception
          case _ =>
        }
      case None => println(s"Could not load resource $fileName")
    }
  }

}
