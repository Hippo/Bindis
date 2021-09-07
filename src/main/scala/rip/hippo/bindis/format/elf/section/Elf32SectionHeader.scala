package rip.hippo.bindis.format.elf.section

import rip.hippo.bindis.format.elf.section.types.ElfSection

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf32SectionHeader(
                                   shName: Int,
                                   shType: Int,
                                   shFlags: Int,
                                   shAddr: Int,
                                   shOffset: Int,
                                   shSize: Int,
                                   shLink: Int,
                                   shInfo: Int,
                                   shAddralign: Int,
                                   shEntsize: Int
                                   ) {
  private var attachment = Option.empty[ElfSection]

  def attach(section: ElfSection): Unit =
    attachment = Option(section)

  def getAttachment[T <: ElfSection]: T =
    attachment.get.asInstanceOf[T]
}
