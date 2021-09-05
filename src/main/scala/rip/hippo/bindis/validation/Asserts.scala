package rip.hippo.bindis.validation

/**
 * @author Hippo
 * @version 1.0.0, 9/1/21
 * @since 1.0.0
 */
object Asserts {
  final case class AssertException(message: String) extends RuntimeException(message)
  
  def assert(condition: Boolean, message: String): Unit =
    if (!condition) throw AssertException(message)
    
  def assertEqual[T](rhs: T, lhs: T, message: String): Unit =
    if (rhs != lhs) throw AssertException(message)
}
