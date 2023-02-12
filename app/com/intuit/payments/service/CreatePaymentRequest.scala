package com.intuit.payments.service

import com.intuit.payments.service.Currency.Currency
import play.api.libs.json.{Json, OFormat}

case class CreatePaymentRequest(amount: Double, currency: Currency, payeeId: String, paymentMethodId: String)

object CreatePaymentRequest {
  implicit val fmt: OFormat[CreatePaymentRequest] = Json.format[CreatePaymentRequest]
}
