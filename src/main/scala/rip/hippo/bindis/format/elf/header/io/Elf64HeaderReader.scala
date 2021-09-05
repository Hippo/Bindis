package rip.hippo.bindis.format.elf.header.io

import rip.hippo.bindis.format.elf.section.io.Elf64SectionHeaderReader
import rip.hippo.bindis.format.elf.ElfReader
import rip.hippo.bindis.format.elf.header.Elf64Header

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf64HeaderReader(elfReader: ElfReader) {

  val header = Elf64Header(
    elfReader.ident,
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readLong(),
    elfReader.inputStream.readLong(),
    elfReader.inputStream.readLong(),
    elfReader.inputStream.readInt(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort(),
    elfReader.inputStream.readShort()
  )

  (0 until header.eShnum).foreach(_ => header.sectionHeaders += Elf64SectionHeaderReader(this).sectionHeader)
}
