package com.intuit.payments.service

import com.intuit.payments.service.exceptions.{InvalidPayeeException, InvalidPaymentMethodException}
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, InjectedController, Request, Result}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PaymentsController @Inject()(paymentsHandler: PaymentHandler) extends InjectedController with Logging {

  /**
   * This API is presumed to have an authentication service proxy that is responsible for authenticating users and then
   * pass a UserID header to this service.
   * If there is no UserID then the service will return UnAuthorized cause only an authenticated user
   * can create a payment method.
   */
  def createPayment(): Action[CreatePaymentRequest] = Action(parse.json[CreatePaymentRequest]).async { implicit request: Request[CreatePaymentRequest] =>
    // TODO: Check that amount is a legal money number? 70.999 is wrong or ok?
    actOnUserID(request)(userId => {
      val payment = Payment(userId, request.body)
      paymentsHandler.createPayment(payment)
        .map {
          case true => Ok(Json.toJson(payment))
          case false => InternalServerError(Json.toJson(""))
        }.recover {
        case _: InvalidPayeeException =>
          logger.error(s"Payee ID ${request.body.payeeId} doesnt exist")
          BadRequest(Json.toJson("Payee ID doesnt exist")) // Currently this can make users know what payeeIds exists or not?
        case _: InvalidPaymentMethodException =>
          logger.error(s"Payment method ID ${request.body.paymentMethodId} doesnt exist for user ID $userId")
          BadRequest(Json.toJson("Payment Method doesnt exist for the User"))
        case ex =>
          logger.error("Unexpected error occurred", ex)
          InternalServerError(Json.toJson(""))
      }
    })
  }

  def getPayees(search: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Future.successful(Ok(Json.toJson(Seq(SearchUserResult("111111", "Peter Parker", "spiderwho@bob.com")))))
  }

  /**
   * This API is presumed to have an authentication service proxy that is responsible for authenticating users and then
   * pass a UserID header to this service.
   * If there is no UserID then the service will return UnAuthorized cause only an authenticated user can get their
   * payment methods.
   */
  def getPaymentMethods: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    actOnUserID(request)(_ => Future.successful(Ok(Json.toJson(Seq(PaymentMethodResult("MasterCard", "*1234"))))))
  }

  private def actOnUserID[T](request: Request[T])(action: String => Future[Result]): Future[Result] = {
    val userId = request.headers.get("UserID")
    userId match {
      case None => Future.successful(Unauthorized(Json.toJson("")))
      case Some(user) => action(user)
    }
  }
}
