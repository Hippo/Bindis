package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.section.types.ElfSection
import rip.hippo.bindis.format.elf.section.io.Elf32SectionHeaderReader

import scala.collection.mutable.ArrayBuffer


/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf32StringTable(data: Array[Byte]) extends ElfSection {
  val size = data.count(_ == 0)

  def get(index: Int): String = {
    var end = index
    while (data(end) != 0) end += 1
    String(data, index, end - index)
  }
}
