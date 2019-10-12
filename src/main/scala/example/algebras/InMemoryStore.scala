package example.algebras

import cats.effect.Sync

import scala.collection.mutable

class InMemoryStore[F[_]](implicit F: Sync[F]) extends Store[F] {
  private val store: mutable.Map[String, String] =
    mutable.Map.empty

  def put(key: String, value: String): F[Unit] =
    F.delay {
      store += key -> value
    }

  def get(key: String): F[Option[String]] =
    F.delay {
      store.get(key)
    }
}
