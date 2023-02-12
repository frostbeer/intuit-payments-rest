package com.intuit.payments.service.models

import com.intuit.payments.service.models.Currency.Currency
import play.api.libs.json.{Json, OFormat}

case class CreatePaymentRequest(amount: Double, currency: Currency, payeeId: String, paymentMethodId: String)

object CreatePaymentRequest {
  implicit val fmt: OFormat[CreatePaymentRequest] = Json.format[CreatePaymentRequest]
}
