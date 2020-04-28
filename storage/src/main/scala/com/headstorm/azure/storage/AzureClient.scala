package com.headstorm.azure.storage

import sttp.client.{ NothingT, SttpBackend }

trait AzureClient[F[_]] {

  def connect(account: String)(implicit backend: SttpBackend[F, fs2.Stream[F, Byte], NothingT]): Unit

}
