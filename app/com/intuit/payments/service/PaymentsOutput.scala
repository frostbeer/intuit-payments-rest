package com.intuit.payments.service

import scala.concurrent.Future

trait PaymentsOutput {
  def output(payment: Payment): Future[Boolean]
}
