package rip.hippo.bindis.format.elf.section.types.impl

import rip.hippo.bindis.format.elf.section.types.ElfSection

/**
 * @author Hippo
 * @version 1.0.0, 9/2/21
 * @since 1.0.0
 */
final case class RawSection(bytes: Array[Byte]) extends ElfSection
