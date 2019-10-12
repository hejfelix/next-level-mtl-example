package example.algebras

import cats.implicits._
import cats.effect.Sync
import cats.mtl.MonadState
import com.olegpy.meow.hierarchy._
import example.MyObjectGraph

object ApplicationLogic {

  /**
    * Use `inspect` to explicitly scope out logger and conf
    */
  def logAppName[F[_]: Sync](implicit F: MonadState[F, MyObjectGraph[F]]) =
    for {
      logger <- F.inspect(_.logger)
      conf   <- F.inspect(_.conf)
      _      <- logger.puts(conf.appName)
    } yield ()

  /**
    * Use next-level mtl to implicitly scope out a Logger[F] from e.g. MyObjectGraph[F]
    */
  def logGreeting[F[_]: Sync](greeting: Option[String])(
      implicit F: MonadState[F, Logger[F]]): F[Unit] =
    for {
      logger <- F.get
      _      <- logger.puts(greeting.mkString)
    } yield ()

  /**
    * Use next-level mtl to implicitly scope out a Store[F] from e.g. MyObjectGraph[F]
    */
  def putAndGet[F[_]: Sync](implicit F: MonadState[F, Store[F]]): F[Option[String]] =
    for {
      store <- F.get
      _     <- store.put("first", "Hello,")
      _     <- store.put("second", "World!")
      hello <- store.get("first")
      world <- store.get("second")
    } yield (hello, world).mapN(_ + " " + _)

  /**
    * Implicitly use lenses (com.olegpy.meow.hierarchy._) to provide dependencies
    * to other function calls.
    */
  def myProgram[F[_]: Sync](implicit F: MonadState[F, MyObjectGraph[F]]): F[Unit] =
    for {
      _        <- logAppName[F]
      greeting <- putAndGet[F]
      _        <- logGreeting(greeting)
    } yield ()
}
