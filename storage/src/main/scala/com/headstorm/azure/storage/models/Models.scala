package com.headstorm.azure.storage.models

final case class Container()

final case class Blob(
  name: String,
  properties: BlobContainerProperties,
  metadata: Map[String, String] = Map[String, String]()
)

final case class BlobContainerProperties(
  )

final case class File()

final case class Table()

final case class Queue()
