package rip.hippo.bindis.format.elf.header

import rip.hippo.bindis.format.elf.constants.ElfConstants
import rip.hippo.bindis.format.elf.section.Elf32SectionHeader
import rip.hippo.bindis.format.elf.section.types.impl.Elf32StringTable

import scala.collection.mutable.ListBuffer

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class Elf32Header(
                              ident: Array[Byte],
                              eType: Short,
                              eMachine: Short,
                              eVersion: Int,
                              eEntry: Int,
                              ePhoff: Int,
                              eShoff: Int,
                              eFlags: Int,
                              eEhsize: Short,
                              ePhentsize: Short,
                              ePhnum: Short,
                              eShentsize: Short,
                              eShnum: Short,
                              eShstrndx: Short
                            ) {
  val sectionHeaders = ListBuffer[Elf32SectionHeader]()

  def getSectionNameStringTable(): Elf32StringTable = {
    val index = if (eShstrndx == ElfConstants.SHN_XINDEX) sectionHeaders(0).shLink else eShstrndx.toInt
    sectionHeaders(index).getAttachment().asInstanceOf[Elf32StringTable]
  }
  
  def getSectionByName(name: String): Option[Elf32SectionHeader] = {
    val table = getSectionNameStringTable()
    sectionHeaders.find(section => table.get(section.shName).equals(name))
  }
}
