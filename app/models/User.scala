package models

import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class User(
    _id: Option[BSONObjectID],
    name: String,
    email: String,
    username: String,
    pwd: String
)
object User {
  implicit val fmt: Format[User] = Json.format[User]
  implicit object UserBSONReader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument): User = {
      User(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("name").get,
        doc.getAs[String]("email").get,
        doc.getAs[String]("username").get,
        doc.getAs[String]("pwd").get
      )
    }
  }

  implicit object UsereBSONWriter extends BSONDocumentWriter[User] {
    def write(user: User): BSONDocument = {
      BSONDocument(
        "_id" -> user._id,
        "name" -> user.name,
        "email" -> user.email,
        "username" -> user.username,
        "pwd" -> user.pwd
      )
    }
  }
}
