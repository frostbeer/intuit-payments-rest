package com.intuit.payments.service

import play.api.Configuration

import javax.inject.{Inject, Singleton}
import pureconfig.generic.auto._

case class Config(kafka: KafkaConfig)

case class KafkaConfig(bootstrapServers: String, topic: String)

@Singleton
class ConfigProvider @Inject() (playConfig: Configuration) {
  private val configRef: Config =
    pureconfig.loadConfigOrThrow[Config](playConfig.underlying, "payments-service")

  def config: Config = configRef
}
