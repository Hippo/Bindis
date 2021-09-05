package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.header.Elf64Header
import rip.hippo.bindis.format.elf.section.io.Elf64SectionHeaderReader
import rip.hippo.bindis.format.elf.section.types.ElfSection
import rip.hippo.bindis.format.elf.util.ElfHash
import rip.hippo.bindis.format.elf.symbol.Elf64Symbol
import rip.hippo.bindis.io.MultiEndianInputStream

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf64HashTable(header: Elf64Header, bucket: Array[Int], chain: Array[Int]) extends ElfSection {
  
  private val nbucket = bucket.length
  private val nchain = chain.length

  def lookup(name: String, symbolTable: Elf64SymbolTable): Option[Elf64Symbol] = {
    val stringTable = header.getSectionNameStringTable()
    val x = ElfHash.hash(name)
    var y = bucket((x % nbucket).toInt)
    while (y != 0) {
      val symbol = symbolTable.entries(y)
      if (name.equals(stringTable.get(symbol.stName))) return Option(symbol)
      y = chain(y)
    }
    Option.empty
  }
}


