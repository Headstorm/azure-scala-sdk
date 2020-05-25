package com.headstorm.azure.storage

import sttp.model.Uri

trait AzureClient[F[_]] {

  def baseURI: Uri

}
