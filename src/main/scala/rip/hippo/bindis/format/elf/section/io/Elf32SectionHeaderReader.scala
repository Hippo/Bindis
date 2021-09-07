package rip.hippo.bindis.format.elf.section.io

import rip.hippo.bindis.format.elf.section.Elf32SectionHeader
import rip.hippo.bindis.format.elf.constants.ElfConstants.*
import rip.hippo.bindis.format.elf.section.types.impl.*
import rip.hippo.bindis.format.elf.header.io.Elf32HeaderReader
import rip.hippo.bindis.format.elf.relocation.{Elf32Relocation, Elf32RelocationAddend}
import rip.hippo.bindis.format.elf.section.types.impl.RawSection
import rip.hippo.bindis.format.elf.symbol.Elf32Symbol
import rip.hippo.bindis.validation.Asserts.*
import rip.hippo.bindis.format.elf.dynamic.Elf32Dynamic
import rip.hippo.bindis.format.elf.note.ElfNote

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf32SectionHeaderReader(elf32HeaderReader: Elf32HeaderReader) {


  private val inputStream = elf32HeaderReader.elfReader.inputStream


  val sectionHeader = Elf32SectionHeader(
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readInt(),
  )

  inputStream.seek(sectionHeader.shOffset)


  sectionHeader.attach(
    sectionHeader.shType match {
      case SHT_SYMTAB | SHT_DYNSYM =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = new Array[Elf32Symbol](size)
        (0 until size).foreach(i => entries(i) = Elf32Symbol(
          inputStream.readInt(),
          inputStream.readInt(),
          inputStream.readInt(),
          inputStream.readUnsignedByte(),
          inputStream.readUnsignedByte(),
          inputStream.readShort()
        ))
        Elf32SymbolTable(entries)
      case SHT_STRTAB =>
        val data = new Array[Byte](sectionHeader.shSize)
        (0 until sectionHeader.shSize).foreach(i => data(i) = inputStream.readByte())
        Elf32StringTable(data)
      case SHT_RELA =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = new Array[Elf32RelocationAddend](size)
        (0 until size).foreach(i => entries(i) = Elf32RelocationAddend(inputStream.readInt(), inputStream.readInt(), inputStream.readInt()))
        Elf32RelocationAddendTable(entries)
      case SHT_HASH =>
        val nbucket = inputStream.readInt()
        val nchain = inputStream.readInt()
        val bucket = new Array[Int](nbucket)
        val chain = new Array[Int](nchain)
        (0 until nbucket).foreach(i => bucket(i) = inputStream.readInt())
        (0 until nchain).foreach(i => chain(i) = inputStream.readInt())
        Elf32HashTable(elf32HeaderReader.header, bucket, chain)
      case SHT_DYNAMIC =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = ListBuffer[Elf32Dynamic]()
        var i = 0
        while (i < size) {
          val entry = Elf32Dynamic(inputStream.readInt(), inputStream.readInt())
          entries += entry
          if (entry.dTag == DT_NULL) {
            i = size
          }
        }
        Elf32DynamicSection(entries)
      case SHT_NOTE =>
        val size = sectionHeader.shSize
        var bytesRead = 0
        val notes = ListBuffer[ElfNote]()
        while (bytesRead < size) {
          val namesz = inputStream.readInt()
          val descsz = inputStream.readInt()
          val nType = inputStream.readInt()
          bytesRead += 12 + namesz + descsz
          val name = new Array[Byte](namesz)
          val desc = new Array[Byte](descsz)
          var read = inputStream.read(name)
          assertEqual(read, namesz, s"Invalid note section, expected=$namesz got=$read")
          inputStream.skip(read % 4)
          read = inputStream.read(desc)
          assertEqual(read, descsz, s"Invalid note section, expected=$descsz got=$read")
          inputStream.skip(read % 4)
          notes += ElfNote(nType, name, desc)
        }
        ElfNoteSection(notes)
      case SHT_REL =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = new Array[Elf32Relocation](size)
        (0 until size).foreach(i => entries(i) = Elf32Relocation(inputStream.readInt(), inputStream.readInt()))
        Elf32RelocationTable(entries)
      case _ =>
        val bytes = new Array[Byte](sectionHeader.shSize)
        val read = inputStream.read(bytes)
        assertEqual(bytes.length, read, s"Invalid section, expected=${bytes.length} got=$read")
        RawSection(bytes)
    }
  )

}
