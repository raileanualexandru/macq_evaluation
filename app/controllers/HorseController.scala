package controllers

import javax.inject._
import play.api.mvc._
import repositories.HorseRepository
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}

import models.Horse
import play.api.libs.json.JsValue

@Singleton
class HorseController @Inject() (implicit
    executionContext: ExecutionContext,
    val horseRepository: HorseRepository,
    val controllerComponents: ControllerComponents
) extends BaseController {
  def findAll(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      horseRepository.findAll().map { horses =>
        Ok(Json.toJson(horses))
      }
  }

  def findOne(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val objectIdTryResult = BSONObjectID.parse(id)
      objectIdTryResult match {
        case Success(objectId) =>
          horseRepository.findOne(objectId).map { horse =>
            Ok(Json.toJson(horse))
          }
        case Failure(_) =>
          Future.successful(BadRequest("Cannot parse the horse id"))
      }
  }

  def create(): Action[JsValue] =
    Action.async(controllerComponents.parsers.json) { implicit request =>
      {

        request.body
          .validate[Horse]
          .fold(
            _ => Future.successful(BadRequest("Cannot parse request body")),
            horse =>
              horseRepository.create(horse).map { _ =>
                Created(Json.toJson(horse))
              }
          )
      }
    }

  def update(id: String): Action[JsValue] =
    Action.async(controllerComponents.parsers.json) { implicit request =>
      {
        request.body
          .validate[Horse]
          .fold(
            _ => Future.successful(BadRequest("Cannot parse request body")),
            horse => {
              val objectIdTryResult = BSONObjectID.parse(id)
              objectIdTryResult match {
                case Success(objectId) =>
                  horseRepository.update(objectId, horse).map { result =>
                    Ok(Json.toJson(result.ok))
                  }
                case Failure(_) =>
                  Future.successful(BadRequest("Cannot parse the horse id"))
              }
            }
          )
      }
    }

  def delete(id: String): Action[AnyContent] = Action.async {
    implicit request =>
      {
        val objectIdTryResult = BSONObjectID.parse(id)
        objectIdTryResult match {
          case Success(objectId) =>
            horseRepository.delete(objectId).map { _ =>
              NoContent
            }
          case Failure(_) =>
            Future.successful(BadRequest("Cannot parse the horse id"))
        }
      }
  }
}
