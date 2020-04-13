package com.headstorm.azurescalasdk.storage

import com.azure.storage.blob._

object Main {

  val blobServiceClient: BlobServiceClient = new BlobServiceClient()

  def main(args: Array[String]): Unit = println("Hello world")

}
