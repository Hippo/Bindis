package rip.hippo.bindis.format.elf.constants

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
object ElfConstants {

  val EI_NIDENT = 16

  val EI_MAG0 = 0
  val EI_MAG1 = 1
  val EI_MAG2 = 2
  val EI_MAG3 = 3
  val ELFMAG0 = 0x7F
  val ELFMAG1 = 0x45
  val ELFMAG2 = 0x4C
  val ELFMAG3 = 0x46

  val EI_CLASS = 4
  val ELFCLASS32 = 1
  val ELFCLASS64 = 2

  val EI_DATA = 5
  val ELFDATA2LSB = 1
  val ELFDATA2MSB = 2

  val EI_VERSION = 6
  val ET_NONE = 0
  val ET_REL = 1
  val ET_EXEC = 2
  val ET_DYN = 3
  val ET_CORE = 4
  val ET_LOPROC = 0xFF00
  val ET_HIPROC = 0xFFFF

  val EI_OSABI = 7

  val EM_NONE = 0
  val EM_M32 = 1
  val EM_SPARC = 2
  val EM_386 = 3
  val EM_68K = 4
  val EM_88K = 5
  val EM_860 = 7
  val EM_MIPS = 8

  val EV_CURRENT = 1

  val SHN_UNDEF = 0
  val SHN_LORESERVE = 0xFF00
  val SHN_LOPROC = 0xFF00
  val SHN_HIPROC = 0xFF1F
  val SHN_ABS = 0xFFF1
  val SHN_COMMON = 0xFFF2
  val SHN_HIRESERVE = 0xFFFF
  val SHN_XINDEX = 0xFFFF

  val SHT_NULL = 0
  val SHT_PROGBITS = 1
  val SHT_SYMTAB = 2
  val SHT_STRTAB = 3
  val SHT_RELA = 4
  val SHT_HASH = 5
  val SHT_DYNAMIC = 6
  val SHT_NOTE = 7
  val SHT_NOBITS = 8
  val SHT_REL = 9
  val SHT_SHLIB = 10
  val SHT_DYNSYM = 11
  val SHT_NUM = 12
  val SHT_LOPROC = 0x70000000
  val SHT_HIPROC = 0x7FFFFFFF
  val SHT_LOUSER = 0x80000000
  val SHT_HIUSER = 0xFFFFFFFF
  
  val SHF_WRITE = 0x1
  val SHF_ALLOC = 0x2
  val SHF_EXECINSTR = 0x4
  val SHF_MASKPROC = 0xF0000000
  
  val STB_LOCAL = 0
  val STB_GLOBAL = 1
  val STB_WEAK = 2
  val STB_LOPROC = 13
  val STB_HIPROC = 15

  val STT_NOTYPE = 0
  val STT_OBJECT = 1
  val STT_FUNC = 2
  val STT_SECTION = 3
  val STT_FILE = 4
  val STT_LOPROC = 13
  val STT_HIPROC = 15

  val DT_NULL = 0
  val DT_NEEDED = 0
  val DT_PLTRELSZ = 2
  val DT_PLTGOT = 3
  val DT_HASH = 4
  val DT_STRTAB = 5
  val DT_SYMTAB = 6
  val DT_RELA = 7
  val DT_RELAENT = 9
  val DT_STRSZ = 10
  val DT_SYMENT = 11
  val DT_INIT = 12
  val DT_FINI = 13
  val DT_SONAME = 14
  val DT_RPATH = 15
  val DT_SYMBOLIC = 16
  val DT_REL = 17
  val DT_RELSZ = 18
  val DT_RELENT = 19
  val DT_PLTREL = 20
  val DT_DEBUG = 21
  val DT_TEXTREL = 22
  val DT_JMPREL = 23
  val DT_LOPROC = 0x70000000
  val DT_HIPROC = 0x7FFFFFFF
}
