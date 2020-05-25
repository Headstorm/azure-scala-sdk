package com.headstorm.azure.storage.config

import pureconfig.ConfigSource
import pureconfig.generic.auto._
import pureconfig._

object Configuration {
  val sharedAccessKey: String = ConfigSource.default.at("azala").load[AzalaConfig] match {
    case Right(configuration) => configuration.sharedAccessKey
    case Left(error) =>
      println(error.toList.toString())
      sys.exit(1)
  }
}

final case class AzalaConfig(sharedAccessKey: String)
