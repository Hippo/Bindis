package rip.hippo.bindis.format.elf.note

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class ElfNote(nType: Int, name: Array[Byte], desc: Array[Byte])
