package com.heastorm.azurescalasdk.storage

import cats.effect.{ ExitCode, IO, IOApp }
import com.headstorm.azure.storage.StorageClient
import com.headstorm.azure.storage.models.{ Blob, BlobContainerProperties }
import org.scalatest.funsuite.AnyFunSuite

class StorageClientTest extends AnyFunSuite with IOApp {

  def run(args: List[String]): IO[ExitCode] = IO.never

  val storageClient = StorageClient.usingClient[IO]("test-account")

  test("should get list of containers") {
    for {
      client <- storageClient
      result <- client.listBlobContainers
    } yield assert(result.toOption.isDefined)
  }

  test("should update a blob") {
    for {
      client <- storageClient
      result <- client.updateBlob(Blob("test", BlobContainerProperties()))
    } yield assert(result.toOption.isDefined)
  }

  test("should get a blob") {
    for {
      client <- storageClient
      result <- client.getBlob("test")
    } yield assert(result.toOption.isDefined)
  }

  test("should get a list of blobs") {
    for {
      client <- storageClient
      result <- client.listBlobs
    } yield assert(result.toOption.isDefined)
  }

}
