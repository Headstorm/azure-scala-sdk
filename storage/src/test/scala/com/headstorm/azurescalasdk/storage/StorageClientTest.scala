package com.headstorm.azurescalasdk.storage

import cats.effect.{ ExitCode, IO, IOApp }
import com.headstorm.azure.storage.StorageClient
import org.scalatest.funsuite.AnyFunSuite

class StorageClientTest extends AnyFunSuite with IOApp {

  test("run integration tests") {
    run(List()).unsafeRunSync()
  }

  def run(args: List[String]): IO[ExitCode] =
    StorageClient
      .usingClient[IO]
      .use[IO, ExitCode] { implicit backend =>
        val storageClient = new StorageClient[IO]("ebaumann")
        (for {
          result1 <- storageClient.createContainer("test").map(_.left.map(b => println(b.body)))
          result2 <- storageClient.getBlobs("test").map(_.left.map(b => println(b.body)))
//          result3 <- storageClient.updateBlob(Blob("test", BlobContainerProperties())).map(_.left.map(b => println(b)))
//          result4 <- storageClient.listBlobs.map(_.left.map(b => println(b.getMessage)))
        } yield {
          assert(result1.isRight)
          assert(result2.isRight)
//          assert(result3.isRight)
//          assert(result4.isRight)
        }).unsafeRunSync()
        IO.pure(ExitCode.Success)
      }
}
