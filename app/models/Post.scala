package models

import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, Oformat}
import reactivemongo.play.json._

case class Post(
    _id: Option[BSONObjectID],
  title: String,
  description: String
)

object Post {
    implicit val format: OFormat[Post] = Json.format[Post]
}