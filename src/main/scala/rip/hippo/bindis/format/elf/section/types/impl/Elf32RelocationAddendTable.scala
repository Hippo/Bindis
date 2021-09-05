package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.relocation.Elf32RelocationAddend
import rip.hippo.bindis.format.elf.section.io.Elf32SectionHeaderReader
import rip.hippo.bindis.format.elf.section.types.ElfSection

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf32RelocationAddendTable(entries: Array[Elf32RelocationAddend]) extends ElfSection
