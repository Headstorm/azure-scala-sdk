package com.headstorm.azure.storage

import com.azure.storage.blob.{ BlobServiceClient, BlobServiceClientBuilder }
import com.headstorm.azure.storage.models.Container
import sttp.client._

/**
 *  Blob, Queue, Table, and File client sdk
 *
 * @tparam F
 */
class StorageClient[F[_]] extends AzureClient[F] {

  private def accountURI(account: String) = uri"https://$account.blob.core.windows.net"

  def connect(account: String)(implicit backend: SttpBackend[F, fs2.Stream[F, Byte], NothingT]): Unit = {

    val blobServiceClient: BlobServiceClient = new BlobServiceClientBuilder().buildClient()
    blobServiceClient.getAccountUrl

    val request = basicRequest.get(uri"${accountURI(account)}")

    val response = request.send()

    println(response)
  }

  /**
   * API to get all containers for an account. A container contains properties, metadata, and zero or more blobs
   *
   * @return a List of Azure Containers
   */
  def listBlobContainers: List[Container] = List()

}
