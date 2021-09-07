package rip.hippo.bindis.format.elf.section.io

import rip.hippo.bindis.format.elf.constants.ElfConstants.*
import rip.hippo.bindis.format.elf.dynamic.Elf64Dynamic
import rip.hippo.bindis.format.elf.section.Elf64SectionHeader
import rip.hippo.bindis.format.elf.header.io.Elf64HeaderReader
import rip.hippo.bindis.format.elf.note.ElfNote
import rip.hippo.bindis.format.elf.relocation.{Elf64Relocation, Elf64RelocationAddend}
import rip.hippo.bindis.format.elf.section.types.impl.{Elf64DynamicSection, Elf64HashTable, Elf64RelocationAddendTable, Elf64RelocationTable, Elf64StringTable, Elf64SymbolTable, ElfNoteSection, RawSection}
import rip.hippo.bindis.format.elf.symbol.Elf64Symbol
import rip.hippo.bindis.validation.Asserts.assertEqual

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf64SectionHeaderReader(elf64HeaderReader: Elf64HeaderReader) {
  
  
  private val inputStream = elf64HeaderReader.elfReader.inputStream
  
  val sectionHeader = Elf64SectionHeader(
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readLong(),
    inputStream.readLong(),
    inputStream.readLong(),
    inputStream.readLong(),
    inputStream.readInt(),
    inputStream.readInt(),
    inputStream.readLong(),
    inputStream.readLong(),
  )


  sectionHeader.attach(
    sectionHeader.shType match {
      case SHT_SYMTAB | SHT_DYNSYM =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = ListBuffer[Elf64Symbol]()
        (0L until size).foreach(_ => entries += Elf64Symbol(
          inputStream.readInt(),
          inputStream.readInt(),
          inputStream.readInt(),
          inputStream.readShort(),
          inputStream.readLong(),
          inputStream.readLong()
        ))
        Elf64SymbolTable(entries)
      case SHT_STRTAB =>
        val data = ListBuffer[Byte]()
        (0L until sectionHeader.shSize).foreach(_ => data += inputStream.readByte())
        Elf64StringTable(data)
      case SHT_RELA =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = ListBuffer[Elf64RelocationAddend]()
        (0L until size).foreach(_ => entries += Elf64RelocationAddend(inputStream.readLong(), inputStream.readLong(), inputStream.readLong()))
        Elf64RelocationAddendTable(entries)
      case SHT_HASH =>
        val nbucket = inputStream.readInt()
        val nchain = inputStream.readInt()
        val bucket = new Array[Int](nbucket)
        val chain = new Array[Int](nchain)
        (0 until nbucket).foreach(i => bucket(i) = inputStream.readInt())
        (0 until nchain).foreach(i => chain(i) = inputStream.readInt())
        Elf64HashTable(elf64HeaderReader.header, bucket, chain)
      case SHT_DYNAMIC =>
        val size = sectionHeader.shSize / sectionHeader.shEntsize
        val entries = ListBuffer[Elf64Dynamic]()
        var i = 0L
        while (i < size) {
          val entry = Elf64Dynamic(inputStream.readLong(), inputStream.readLong())
          entries += entry
          if (entry.dTag == DT_NULL) {
            i = size
          }
        }
        Elf64DynamicSection(entries)
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
        val entries = ListBuffer[Elf64Relocation]()
        (0L until size).foreach(i => entries += Elf64Relocation(inputStream.readLong(), inputStream.readLong()))
        Elf64RelocationTable(entries)
      case _ =>
        val bytes = new Array[Byte](sectionHeader.shSize.toInt)
        val read = inputStream.read(bytes)
        assertEqual(bytes.length, read, s"Invalid null section, expected=${bytes.length} got=$read")
        RawSection(bytes)
    }
  )

}
