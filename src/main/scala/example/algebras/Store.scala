package example.algebras

trait Store[F[_]] {
  def put(key: String, value: String): F[Unit]
  def get(key: String): F[Option[String]]
}
