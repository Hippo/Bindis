package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.section.io.Elf64SectionHeaderReader
import rip.hippo.bindis.format.elf.section.types.ElfSection
import rip.hippo.bindis.format.elf.symbol.Elf64Symbol

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf64SymbolTable(entries: ListBuffer[Elf64Symbol]) extends ElfSection
