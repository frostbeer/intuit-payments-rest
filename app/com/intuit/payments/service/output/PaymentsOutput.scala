package com.intuit.payments.service.output

import com.intuit.payments.service.models.Payment

import scala.concurrent.Future

trait PaymentsOutput {
  def output(payment: Payment): Future[Boolean]
}
