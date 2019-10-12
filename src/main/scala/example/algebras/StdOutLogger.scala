package example.algebras

import cats.effect.Sync

class StdOutLogger[F[_]](implicit F: Sync[F]) extends Logger[F] {
  override def puts(s: String): F[Unit] = F.delay(println(s))
}
