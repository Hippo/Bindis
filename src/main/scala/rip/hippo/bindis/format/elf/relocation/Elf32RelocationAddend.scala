package rip.hippo.bindis.format.elf.relocation

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
final case class Elf32RelocationAddend(
                                rOffset: Int,
                                rInfo: Int,
                                rAddend: Int
                                )
