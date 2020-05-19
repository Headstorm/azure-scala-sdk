package com.headstorm.azure.storage.models

final case class Container(
  //TODO
)

final case class Blob(
  name: String,
  properties: BlobContainerProperties,
  metadata: Map[String, String] = Map[String, String]()
)

final case class BlobContainerProperties(
  //TODO
)

final case class File()

final case class Table()

final case class Queue()
