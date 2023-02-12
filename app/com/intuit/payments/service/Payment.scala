package com.intuit.payments.service

import com.intuit.payments.service.Currency.Currency
import play.api.libs.json.{Json, OFormat}

import java.time.{LocalDateTime, ZoneId}

case class Payment(amount: Double,
                   currency: Currency,
                   userId: String,
                   payeeId: String,
                   paymentMethodId: String,
                   createdAt: LocalDateTime)

object Payment {
  implicit val fmt: OFormat[Payment] = Json.format[Payment]

  def apply(userId: String, request: CreatePaymentRequest): Payment = {
    new Payment(
      amount = request.amount,
      currency = request.currency,
      userId = userId,
      payeeId = request.payeeId,
      paymentMethodId = request.paymentMethodId,
      createdAt = LocalDateTime.now(ZoneId.of("UTC"))
    )
  }
}
