package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.dynamic.Elf64Dynamic
import rip.hippo.bindis.format.elf.section.types.ElfSection

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf64DynamicSection(entries: ListBuffer[Elf64Dynamic]) extends ElfSection
