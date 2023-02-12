package com.intuit.payments.service

import com.intuit.payments.service.models.Payment
import com.intuit.payments.service.output.PaymentsOutput

import javax.inject.Inject
import scala.concurrent.Future

class PaymentHandler @Inject()(paymentsOutput: PaymentsOutput) {
  def createPayment(payment: Payment): Future[Boolean] = {
    //TODO: check if the payment method is even belong to the userId?
    //TODO: check if the payeeId even exists?
    paymentsOutput.output(payment)
  }
}
