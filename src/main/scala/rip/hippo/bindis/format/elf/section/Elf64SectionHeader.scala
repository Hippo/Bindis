package rip.hippo.bindis.format.elf.section

import rip.hippo.bindis.format.elf.section.types.ElfSection

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf64SectionHeader(
                                   shName: Int,
                                   shType: Int,
                                   shFlags: Long,
                                   shAddr: Long,
                                   shOffset: Long,
                                   shSize: Long,
                                   shLink: Int,
                                   shInfo: Int,
                                   shAddralign: Long,
                                   shEntsize: Long
                                   ) {
  private var attachment = Option.empty[ElfSection]

  def attach(section: ElfSection): Unit =
    attachment = Option(section)

  def getAttachment(): ElfSection =
    attachment.get

}