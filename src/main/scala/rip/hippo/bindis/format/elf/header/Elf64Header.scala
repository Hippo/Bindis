package rip.hippo.bindis.format.elf.header

import rip.hippo.bindis.format.elf.constants.ElfConstants
import rip.hippo.bindis.format.elf.section.Elf64SectionHeader
import rip.hippo.bindis.format.elf.section.types.impl.Elf64StringTable

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf64Header(
                              ident: Array[Byte],
                              eType: Short,
                              eMachine: Short,
                              eVersion: Int,
                              eEntry: Long,
                              ePhoff: Long,
                              eShoff: Long,
                              eFlags: Int,
                              eEhsize: Short,
                              ePhentsize: Short,
                              ePhnum: Short,
                              eShentsize: Short,
                              eShnum: Short,
                              eShstrndx: Short
                            ) {
  val sectionHeaders = ListBuffer[Elf64SectionHeader]()
  
  def getSectionNameStringTable(): Elf64StringTable = {
    val index = if (eShstrndx == ElfConstants.SHN_XINDEX) sectionHeaders(0).shLink else eShstrndx.toInt

    sectionHeaders(index).getAttachment().asInstanceOf[Elf64StringTable]
  }

  def getSectionByName(name: String): Option[Elf64SectionHeader] = {
    val table = getSectionNameStringTable()
    sectionHeaders.find(section => table.get(section.shName).equals(name))
  }
}
