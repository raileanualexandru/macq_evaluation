package controllers

import javax.inject._
import play.api.mvc._
import repositories.UserRepository
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}

import models.User
import play.api.libs.json.JsValue

@Singleton
class UserController @Inject() (implicit
    executionContext: ExecutionContext,
    val userRepository: UserRepository,
    val controllerComponents: ControllerComponents
) extends BaseController {


  def create(): Action[JsValue] =
    Action.async(controllerComponents.parsers.json) { implicit request =>
      {

        request.body
          .validate[User]
          .fold(
            _ => Future.successful(BadRequest("Cannot parse request body")),
            user =>
              userRepository.create(user).map { _ =>
                Created(Json.toJson(user))
              }
          )
      }
    }
   

}

