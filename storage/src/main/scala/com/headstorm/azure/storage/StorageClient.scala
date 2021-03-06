package com.headstorm.azure.storage

import cats.effect.{ ConcurrentEffect, ContextShift, Resource }
import cats.implicits._
import com.headstorm.azure.storage.headers.Headers
import com.headstorm.azure.storage.models.{ ListBlobResponse, ListContainerResponse }
import sttp.client.circe._
import io.circe.{ Error => CirceError }
import io.circe.generic.auto._
import org.http4s.client.blaze.BlazeClientBuilder
import sttp.client._
import sttp.model.Uri

import scala.concurrent.ExecutionContext

/**
 *  Blob, Queue, Table, and File client sdk
 *
 * @tparam F Effectful type
 */
object StorageClient {

  def usingClient[F[_]: ConcurrentEffect: ContextShift]: Resource[F, SttpBackend[F, Nothing, Nothing]] = {
    import sttp.client.http4s._
    val client = BlazeClientBuilder.apply[F](ExecutionContext.global)
    Http4sBackend
      .usingClientBuilder[F](client, BlockingIO.blockingContext("azure-storage-client"))
  }
}

class StorageClient[F[_]: ConcurrentEffect: ContextShift](account: String)(
  implicit val backend: SttpBackend[F, Nothing, Nothing]
) extends AzureClient[F] {

  val baseURI: Uri = uri"https://$account.blob.core.windows.net"

  /**
   * API to get all containers for an account. A container contains properties, metadata, and zero or more blobs
   *
   * @return a List of Azure Containers
   */
  def createContainer(
    container: String,
    includeMetaData: Option[Boolean] = None,
    timeoutSeconds: Option[Integer] = None
  ): F[Either[ResponseError[CirceError], ListContainerResponse]] =
    basicRequest
      .header(Headers.auth(account))
      .header(Headers.version)
      .header(Headers.date)
      .put(
        uri"$baseURI/$container?restype=container&includeMetaData=$includeMetaData&timeout=$timeoutSeconds"
      )
      .contentType("application/json")
      .response(asJson[ListContainerResponse])
      .send()
      .map(_.body)

  /**
   * API to get all blobs for a container. A container contains properties, metadata, and zero or more blobs
   *
   * @return a List of Azure Blobs in a specified container
   */
  def getBlobs(
    container: String,
    prefix: Option[String] = None,
    marker: Option[String] = None,
    maxResults: Option[Integer] = None,
    includeMetaData: Option[Boolean] = None,
    timeoutSeconds: Option[Integer] = None
  ): F[Either[ResponseError[CirceError], ListBlobResponse]] =
    basicRequest
      .header(Headers.auth(account))
      .header(Headers.version)
      .header(Headers.date)
      .get(
        uri"$baseURI/$container?restype=container&comp=list&prefix=$prefix&marker=$marker&maxResults=$maxResults&includeMetaData=$includeMetaData&timeout=$timeoutSeconds"
      )
      .contentType("application/json")
      .response(asJson[ListBlobResponse])
      .send()
      .map(_.body)

}
