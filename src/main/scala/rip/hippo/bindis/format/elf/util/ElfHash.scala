package rip.hippo.bindis.format.elf.util

/**
 * @author Hippo
 * @version 1.0.0, 9/3/21
 * @since 1.0.0
 */
object ElfHash {
  def hash(name: String): Long = {
    var h = 0L
    var g = 0L
    var i = 0
    val bytes = name.getBytes
    while (bytes(i) != 0) {
      h = (h << 4) + bytes(i)
      i += 1
      g = h & 0xF0000000L
      if (g != 0)
        h ^= g >> 24L

      h &= ~g
    }
    h
  }
}
