package rip.hippo.bindis.format.elf.symbol

/**
 * @author Hippo
 * @version 1.0.0, 9/2/21
 * @since 1.0.0
 */
final case class Elf32Symbol(
                            stName: Int,
                            stValue: Int,
                            stSize: Int,
                            stInfo: Int,
                            stOther: Int,
                            stShndx: Short
                            ):
  val stBind: Int = (stInfo & 0xFF) >> 4
  val stType: Int = stInfo & 0xF

