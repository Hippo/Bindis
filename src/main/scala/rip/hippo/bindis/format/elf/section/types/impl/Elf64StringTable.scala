package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.section.io.Elf64SectionHeaderReader
import rip.hippo.bindis.format.elf.section.types.ElfSection

import scala.collection.mutable.{ArrayBuffer, ListBuffer}


/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf64StringTable(data: ListBuffer[Byte]) extends ElfSection {

  val size = data.count(_ == 0)
  private val toArray = data.toArray

  def get(index: Int): String = {
    var end = index
    while (toArray(end) != 0) end += 1
    String(toArray, index, end - index)
  }
}
