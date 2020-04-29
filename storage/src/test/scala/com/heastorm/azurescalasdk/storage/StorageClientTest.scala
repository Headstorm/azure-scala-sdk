package com.heastorm.azurescalasdk.storage

import cats.effect.IOApp
import com.headstorm.azure.storage.StorageClient
import org.scalatest._

class StorageClientTest extends FunSuite with IOApp {

  val storageClient = StorageClient.usingClient("test-account")

  test("1 should equal 3 - 2") {
    assert(1 == 3 - 2)
  }

}
