package example

import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp}
import cats.mtl.MonadState
import com.olegpy.meow.effects._
import example.algebras.{InMemoryStore, Logger, StdOutLogger, Store}

case class MyObjectGraph[F[_]](conf: Configuration, store: Store[F], logger: Logger[F])

case class Configuration(appName: String)

object Hello extends IOApp {
  import example.algebras.ApplicationLogic.myProgram

  /**
    * Construct our object graph. We simply make a "flat" structure and
    * functions can ask for dependencies implicitly using next-level mtl.
    */
  override def run(args: List[String]): IO[ExitCode] =
    for {
      ref <- Ref[IO].of(
        MyObjectGraph(
          Configuration("Next-Level"),
          new InMemoryStore[IO],
          new StdOutLogger[IO]
        )
      )
      _ <- ref.runState { implicit monadState: MonadState[IO, MyObjectGraph[IO]] =>
        myProgram[IO]
      }
    } yield ExitCode.Success

}
