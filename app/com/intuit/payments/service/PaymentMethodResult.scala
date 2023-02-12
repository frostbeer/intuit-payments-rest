package com.intuit.payments.service

import play.api.libs.json.{Json, OFormat}

case class PaymentMethodResult(method: String, lastDigits: String)

object PaymentMethodResult {
  implicit val fmt: OFormat[PaymentMethodResult] = Json.format[PaymentMethodResult]
}
