package models

import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class Horse(
    _id: Option[BSONObjectID],
    name: String,
    colour: String,
    speed: Int,
    breed: String,
    image_src: String
)

object Horse {
  implicit val fmt: Format[Horse] = Json.format[Horse]
  implicit object HorseBSONReader extends BSONDocumentReader[Horse] {
    def read(doc: BSONDocument): Horse = {
      Horse(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("name").get,
        doc.getAs[String]("colour").get,
        doc.getAs[Int]("speed").get,
        doc.getAs[String]("breed").get,
        doc.getAs[String]("image_src").get
      )
    }
  }

  implicit object HorseBSONWriter extends BSONDocumentWriter[Horse] {
    def write(horse: Horse): BSONDocument = {
      BSONDocument(
        "_id" -> horse._id,
        "name" -> horse.name,
        "colour" -> horse.colour,
        "speed" -> horse.speed,
        "breed" -> horse.breed,
        "image_src" -> horse.image_src
      )
    }
  }
}
