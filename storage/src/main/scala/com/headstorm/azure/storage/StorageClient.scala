package com.headstorm.azure.storage

import com.azure.storage.blob.{ BlobServiceClient, BlobServiceClientBuilder }
import sttp.client.HttpURLConnectionBackend
import sttp.client._

class StorageClient[F[_]] extends AzureClient[F] {

  private implicit val backend = HttpURLConnectionBackend()

  def connect(): Unit = {

    val blobServiceClient: BlobServiceClient = new BlobServiceClientBuilder().buildClient()
    blobServiceClient.getAccountUrl

    // the `query` parameter is automatically url-encoded
    // `sort` is removed, as the value is not defined
    val request = basicRequest.get(uri"https://api.github.com/search/repositories")

    val response = request.send()

    println(response.body)
  }
}
