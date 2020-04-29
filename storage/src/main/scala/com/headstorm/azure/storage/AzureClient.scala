package com.headstorm.azure.storage

import com.headstorm.azure.storage.auth.Authorize
import sttp.model.Uri

trait AzureClient[F[_]] {

  def baseURI: Uri
  def accessToken: String = Authorize.auth()

}
