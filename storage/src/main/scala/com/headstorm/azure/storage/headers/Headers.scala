package com.headstorm.azure.storage.headers

import java.text.SimpleDateFormat
import java.util.{ Calendar, TimeZone }

import com.headstorm.azure.storage.config.Configuration
import sttp.model.Header

object Headers {

  def auth(accountName: String): Header = {
    val key = Configuration.sharedAccessKey
    Header("Authorization", s"SharedKey $accountName:$key")
  }

  def version: Header = {
    val version = "2019-07-07"
    Header("x-ms-version", version)
  }

  def date: Header = {
    val fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
    fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    val date = fmt.format(Calendar.getInstance.getTime) + " GMT";
    Header("x-ms-date", date)

  }

}
