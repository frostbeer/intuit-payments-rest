package com.intuit.payments.service.models

import play.api.libs.json.{Json, OFormat}

case class SearchUserResult(userId: String, name: String, email: String)

object SearchUserResult {
  implicit val fmt: OFormat[SearchUserResult] = Json.format[SearchUserResult]
}
