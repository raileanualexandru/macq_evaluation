
package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class AppController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(Json.obj("content" -> "app works good!"))
  }
}

