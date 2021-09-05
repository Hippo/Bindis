package rip.hippo.bindis.io

import rip.hippo.bindis.endian.Endianness

import java.io.{ByteArrayInputStream, DataInputStream, EOFException, InputStream}

import rip.hippo.bindis.validation.Asserts._

/**
 * @author Hippo
 * @version 1.0.0, 8/31/21
 * @since 1.0.0
 */
final case class MultiEndianInputStream(inputStream: ByteArrayInputStream) extends InputStream {

  var endianness: Endianness = Endianness.BigEndian

  override def read(): Int =
    inputStream.read()

  def useLittleEndian(): Unit =
    endianness = Endianness.LittleEndian


  private def toInt16(b1: Byte, b2: Byte): Int = {
    var t1 = b1
    var t2 = b2
    if (endianness == Endianness.LittleEndian) {
      t1 = b2
      t2 = b1
    }
    (t1 & 0xFF) << 8 | t2 & 0xFF
  }

  private def toInt(b1: Byte, b2: Byte, b3: Byte, b4: Byte): Int = {
    var t1 = b1
    var t2 = b2
    var t3 = b3
    var t4 = b4
    if (endianness == Endianness.LittleEndian) {
      t1 = b4
      t2 = b3
      t3 = b2
      t4 = b1
    }
    t1 << 24 | (t2 & 0xFF) << 16 | (t3 & 0xFF) << 8 | (t4 & 0xFF)
  }

  private def toLong(b1: Byte, b2: Byte, b3: Byte, b4: Byte, b5: Byte, b6: Byte, b7: Byte, b8: Byte): Long = {
    var t1 = b1
    var t2 = b2
    var t3 = b3
    var t4 = b4
    var t5 = b5
    var t6 = b6
    var t7 = b7
    var t8 = b8
    if (endianness == Endianness.LittleEndian) {
      t1 = b8
      t2 = b7
      t3 = b6
      t4 = b5
      t5 = b4
      t6 = b3
      t7 = b2
      t8 = b1
    }
    (t1 & 0xFFL) << 56 | (t2 & 0xFFL) << 48 | (t3 & 0xFFL) << 40 | (t4 & 0xFFL) << 32 | (t5 & 0xFFL) << 24 | (t6 & 0xFFL) << 16 | (t7 & 0xFFL) << 8 | (t8 & 0xFFL)
  }



  def readByte(): Byte = {
    val b = inputStream.read()
    if (b < 0) throw new EOFException()
    b.toByte
  }

  def readUnsignedByte(): Int = readByte() & 0xFF
  
  def readUnsignedShort(): Int =
    toInt16(readByte(), readByte())

  def readShort(): Short = readUnsignedShort().toShort

  def readInt(): Int =
    toInt(readByte(), readByte(), readByte(), readByte())

  def readLong(): Long =
    toLong(readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte())


  def seek(offset: Long): Unit = {
    inputStream.reset()
    assertEqual(inputStream.skip(offset), offset, "Attempted to seek outside of file.")
  }

  override def available(): Int =
    inputStream.available()
}
