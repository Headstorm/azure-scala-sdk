package com.headstorm.azure.storage

import cats.effect.{ ConcurrentEffect, ContextShift, Sync, Timer }
import cats.implicits._
import com.headstorm.azure.storage.models.{ Blob, Container }
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

  def usingClient[F[_]: ConcurrentEffect: ContextShift: Timer](
    account: String
  )(implicit sync: Sync[F]): F[StorageClient[F]] = {
    import sttp.client.http4s._
    val client = BlazeClientBuilder.apply[F](ExecutionContext.global)
    Http4sBackend
      .usingClientBuilder[F](client, BlockingIO.blockingContext("azure-storage-client"))
      .use[F, StorageClient[F]](implicit backend => sync.pure(new StorageClient[F](account)))
  }
}

class StorageClient[F[_]: ConcurrentEffect: ContextShift: Sync: Timer](account: String)(
  implicit val backend: SttpBackend[F, fs2.Stream[F, Byte], NothingT]
) extends AzureClient[F] {

  val baseURI: Uri = uri"https://$account.blob.core.windows.net"

  /**
   * API to get all containers for an account. A container contains properties, metadata, and zero or more blobs
   *
   * @return a List of Azure Containers
   */
  def listBlobContainers: F[Either[ResponseError[CirceError], Container]] =
    basicRequest.auth
      .bearer(accessToken)
      .get(uri"$baseURI/containers?8989")
      .response(asJson[Container])
      .send()
      .map(_.body)

  def listBlobs: F[Either[ResponseError[CirceError], List[Blob]]] =
    basicRequest.auth.bearer(accessToken).get(uri"$baseURI/blobs").response(asJson[List[Blob]]).send().map(_.body)

  def updateBlob(blob: Blob): F[Either[String, String]] =
    basicRequest.auth
      .bearer(accessToken)
      .body(blob)
      .post(uri"$baseURI/updateblob")
      .send
      .map(_.body)

  def getBlob: F[Either[ResponseError[CirceError], Blob]] =
    basicRequest.auth.bearer(accessToken).get(uri"$baseURI/getblob").response(asJson[Blob]).send().map(_.body)

}
