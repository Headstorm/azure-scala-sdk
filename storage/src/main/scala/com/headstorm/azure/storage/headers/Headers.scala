package com.headstorm.azure.storage.headers

import java.text.SimpleDateFormat
import java.util.{ Calendar, TimeZone }

import com.headstorm.azure.storage.config.Configuration
import sttp.model.Header

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Headers {

  // hash the signature string using the account key secret
  def getHmacSHA256(key: String, content: String): Array[Byte] = {
    val secret = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256")
    val mac    = Mac.getInstance("HmacSHA256")
    mac.init(secret)
    mac.doFinal(content.getBytes("UTF-8"))
  }

  // encode the utf-8 byte array to a base 64 string
  def encodeToBase64String(bytes: Array[Byte]): String = Base64.getEncoder.encodeToString(bytes)

  // construct the signature string using the request info
  def constructSignatureString(
    verb: String,
    contentEncoding: String = "",
    contentLanguage: String = "",
    contentLength: String = "",
    contentMD5: String = "",
    contentType: String = "application/json",
    date: String = "",
    ifModifiedSince: String = "",
    ifMatch: String = "",
    ifNoneMatch: String = "",
    ifUnmodifiedSince: String = "",
    range: String = "",
    canonicalizedHeaders: String = "",
    canonicalizedResource: String = ""
  ): String =
    verb + "\n" +
      contentEncoding + "\n" +
      contentLanguage + "\n" +
      contentLength + "\n" +
      contentMD5 + "\n" +
      contentType + "\n" +
      date + "\n" +
      ifModifiedSince + "\n" +
      ifMatch + "\n" +
      ifNoneMatch + "\n" +
      ifUnmodifiedSince + "\n" +
      range + "\n" +
      canonicalizedHeaders + "\n" +
      canonicalizedResource;

  // to create the auth
  def auth(accountName: String, verb: String, resource: String = ""): Header = {
    val key     = Configuration.storageAccountKey
    val headers = (date.toString() + "\n" + version.toString()).replace(": ", ":");

    verb match {
      case "GET" | "PUT" | "POST" | "DELETE" =>
        val StringToSign =
          constructSignatureString(
            verb,
            canonicalizedHeaders = headers,
            canonicalizedResource = resource
          )
        println(s"String to sign: $StringToSign")
        val hashedKey  = encodeToBase64String(getHmacSHA256(key, StringToSign));
        Header("Authorization", s"SharedKey $accountName:$hashedKey");
      case _ =>
        println("Invalid method verb for auth")
        sys.exit(1)
    }
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
