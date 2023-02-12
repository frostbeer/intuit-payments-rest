package com.intuit.payments.service.output

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.SendProducer
import com.intuit.payments.service.ConfigProvider
import com.intuit.payments.service.models.Payment
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import play.api.Logging
import play.api.libs.json.Json

import javax.inject.Inject
import scala.concurrent.Future

class PaymentsKafkaOutput @Inject()(configProvider: ConfigProvider, implicit val system: ActorSystem) extends PaymentsOutput with Logging {

  private implicit val ec = system.dispatcher
  private val producer: SendProducer[String, String] = SendProducer(kafkaProducerSettings())

  def output(payment: Payment): Future[Boolean] = {
    val paymentJsonStr = Json.stringify(Json.toJson(payment))
    val kafkaMessage = new ProducerRecord(configProvider.config.kafka.topic, payment.userId, paymentJsonStr)
    producer.send(kafkaMessage)
      .map(_ => true)
      .recover(f => {
        //TODO: Add a metric here for fail to publish message
        logger.error("error", f)
        false
      })
  }

  private def kafkaProducerSettings() = {
    ProducerSettings(system, new StringSerializer, new StringSerializer)
      .withBootstrapServers(configProvider.config.kafka.bootstrapServers)
  }
}
