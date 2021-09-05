package rip.hippo.bindis.format.elf

import rip.hippo.bindis.io.MultiEndianInputStream
import rip.hippo.bindis.format.elf.constants.ElfConstants.*
import rip.hippo.bindis.format.elf.header.io.Elf64HeaderReader
import rip.hippo.bindis.format.elf.header.io.{Elf32HeaderReader, Elf64HeaderReader}
import rip.hippo.bindis.validation.Asserts.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
final case class ElfReader(inputSource: InputStream | Array[Byte]) extends AutoCloseable {

  private[elf] val inputStream = inputSource match {
    case inputStream: InputStream =>
      def toByteArray(): Array[Byte] = {
        val byteArrayOutputStream = new ByteArrayOutputStream()
        val length = inputStream.available()
        val buffer = new Array[Byte](length)
        def read(): Unit = {
          val len = inputStream.read(buffer)
          if (len > 0) {
            byteArrayOutputStream.write(buffer, 0, len)
            read()
          }
        }
        read()
        byteArrayOutputStream.toByteArray
      }
      MultiEndianInputStream(new ByteArrayInputStream(toByteArray()))
    case byteArray: Array[Byte] => MultiEndianInputStream(new ByteArrayInputStream(byteArray))
  }

  private[elf] val ident = new Array[Byte](EI_NIDENT)
  private val read = inputStream.read(ident)
  assertEqual(ident.length, read, s"Failed to read elf header, expected=$EI_NIDENT actual=$read")
  assert(ident(EI_MAG0) == ELFMAG0 && ident(EI_MAG1) == ELFMAG1 && ident(EI_MAG2) == ELFMAG2 && ident(EI_MAG3) == ELFMAG3, "Invalid magic number.")

  private val eiClass = ident(EI_CLASS)
  assert(eiClass == ELFCLASS32 || eiClass == ELFCLASS64, s"Invalid file class: $eiClass")

  private val eiData = ident(EI_DATA)
  assert(eiData == ELFDATA2LSB || eiData == ELFDATA2MSB, s"Invalid file encoding: $eiData")

  if (eiData == ELFDATA2LSB) inputStream.useLittleEndian()

  assertEqual(ident(EI_VERSION), EV_CURRENT, s"Invalid ELF header version: ${ident(EI_VERSION)}")

  private val osabi = ident(EI_OSABI)

  val elf32Header = if (eiClass == ELFCLASS32) Option(Elf32HeaderReader(this)) else Option.empty
  val elf64Header = if (eiClass == ELFCLASS64) Option(Elf64HeaderReader(this)) else Option.empty
  

  override def close(): Unit = inputStream.close()
}
