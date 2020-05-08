package com.headstorm.azure.storage.models

import java.util.Date

import com.headstorm.azure.storage.models.LeaseDuration.LeaseDuration
import com.headstorm.azure.storage.models.LeaseState.LeaseState
import com.headstorm.azure.storage.models.LeaseStatus.LeaseStatus
import com.headstorm.azure.storage.models.PublicAccess.PublicAccess

object LeaseStatus extends Enumeration {
  type LeaseStatus = Value
  val locked, unlocked = Value
}

object LeaseState extends Enumeration {
  type LeaseState = Value
  val available, leased, expired, breaking, broken = Value
}

object LeaseDuration extends Enumeration {
  type LeaseDuration = Value
  val infinite, fixed = Value
}

object PublicAccess extends Enumeration {
  type PublicAccess = Value
  val container, blob = Value
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
  `Last-Modified`: Date,
  Etag: String,
  LeaseStatus: LeaseStatus,
  LeaseState: LeaseState,
  LeaseDuration: LeaseDuration,
  PublicAccess: PublicAccess,
  HasImmutabilityPolicy: Boolean,
  HasLegalHold : Boolean,
  MetaData: MetaData
)

final case class Container(
  Name: String,
  ContainerProperties : ContainerProperties
)

final case class Blob(
  name: String,
  properties: BlobContainerProperties,
  metadata: Map[String, String] = Map[String, String]()
)

final case class BlobContainerProperties()

final case class File()

final case class Table()

final case class Queue()
