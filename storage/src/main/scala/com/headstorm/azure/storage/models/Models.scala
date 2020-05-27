package com.headstorm.azure.storage.models

import java.time.LocalDateTime
import io.circe.generic.auto._
import io.circe.syntax._

import enumeratum._

sealed trait LeaseStatus extends EnumEntry

case object LeaseStatus extends Enum[LeaseStatus] with CirceEnum[LeaseStatus] {

  case object locked   extends LeaseStatus
  case object unlocked extends LeaseStatus

  val values = findValues

}

sealed trait LeaseState extends EnumEntry

case object LeaseState extends Enum[LeaseState] with CirceEnum[LeaseState] {

  case object available extends LeaseState
  case object leased    extends LeaseState
  case object expired   extends LeaseState
  case object breaking  extends LeaseState
  case object broken    extends LeaseState

  val values = findValues

}

sealed trait LeaseDuration extends EnumEntry

case object LeaseDuration extends Enum[LeaseDuration] with CirceEnum[LeaseDuration] {

  case object infinite extends LeaseDuration
  case object fixed    extends LeaseDuration

  val values = findValues

}

sealed trait PublicAccess extends EnumEntry

case object PublicAccess extends Enum[PublicAccess] with CirceEnum[PublicAccess] {

  case object container extends PublicAccess
  case object blob      extends PublicAccess

  val values = findValues

}

final case class ListContainerResponse(
  Prefix: Option[String] = None,
  Marker: String,
  NextMarker: String,
  MaxResults: Int,
  Containers: List[Container]
)

final case class MetaData(
  `meta-data`: String
)

final case class ContainerProperties(
  `Last-Modified`: LocalDateTime,
  Etag: String,
  LeaseStatus: LeaseStatus,
  LeaseState: LeaseState,
  LeaseDuration: LeaseDuration,
  PublicAccess: PublicAccess,
  HasImmutabilityPolicy: Boolean,
  HasLegalHold: Boolean,
  MetaData: MetaData
)

final case class Container(
  Name: String,
  ContainerProperties: ContainerProperties
)

final case class ListBlobResponse(
  Prefix: Option[String] = None,
  Marker: String,
  NextMarker: String,
  MaxResults: Int,
  Blobs: List[Blob]
)

final case class Blob(
  name: String,
  properties: BlobContainerProperties,
  metadata: Map[String, String] = Map[String, String]()
) {
  def toJson: String = this.asJson.spaces4
}

final case class BlobContainerProperties()

final case class File()

final case class Table()

final case class Queue()
