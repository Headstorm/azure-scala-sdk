package com.heastorm.azurescalasdk.storage

import cats.effect.{ExitCode, IO, IOApp}
import com.headstorm.azure.storage.StorageClient
import org.scalatest.funsuite.AnyFunSuite


class StorageClientTest extends AnyFunSuite with IOApp {

  val storageClient = StorageClient.usingClient[IO]("test-account")

  test("1 should equal 3 - 2") {
    assert(1 == 3 - 2)
  }

  override def run(args: List[String]): IO[ExitCode] = {IO.never}

}
