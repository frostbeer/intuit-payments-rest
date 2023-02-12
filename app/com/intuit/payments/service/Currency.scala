package com.intuit.payments.service

import play.api.libs.json.{Format, Json}

object Currency extends Enumeration {
  type Currency = Value

  val USD: Currency.Value = Value("USD")
  val EUR: Currency.Value = Value("EUR")
  val CAD: Currency.Value = Value("CAD")
  val GBP: Currency.Value = Value("GBP")

  implicit val format: Format[Currency] = Json.formatEnum(this)
}
