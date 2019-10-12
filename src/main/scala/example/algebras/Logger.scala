package example.algebras

trait Logger[F[_]] {
  def puts(s: String): F[Unit]
}
