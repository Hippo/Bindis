package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.section.types.ElfSection
import rip.hippo.bindis.format.elf.note.ElfNote

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class ElfNoteSection(notes: ListBuffer[ElfNote]) extends ElfSection
