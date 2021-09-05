package rip.hippo.bindis.format.elf.header.io

import rip.hippo.bindis.format.elf.ElfReader
import rip.hippo.bindis.format.elf.header.Elf32Header
import rip.hippo.bindis.format.elf.section.Elf32SectionHeader
import rip.hippo.bindis.format.elf.section.io.Elf32SectionHeaderReader

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf32HeaderReader(elfReader: ElfReader) {

  val header = Elf32Header(
    elfReader.ident,
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort()
  )


  (0 until header.eShnum).foreach(i => {
    val offset = header.eShoff + (i * header.eShentsize)
    elfReader.inputStream.seek(offset)
    header.sectionHeaders += Elf32SectionHeaderReader(this).sectionHeader
  })
}
