package repositories

import models.Horse
import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import org.joda.time.DateTime
import reactivemongo.api.commands.WriteResult

@Singleton
class HorseRepository @Inject() (implicit
    executionContext: ExecutionContext,
    reactiveMongoApi: ReactiveMongoApi
) {
  def collection: Future[BSONCollection] =
    reactiveMongoApi.database.map(db => db.collection("horses"))

  def findAll(limit: Int = 100): Future[Seq[Horse]] = {

    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Horse])
        .cursor[Horse](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Horse]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Horse]] = {
    collection.flatMap(
      _.find(BSONDocument("_id" -> id), Option.empty[Horse]).one[Horse]
    )
  }

  def create(horse: Horse): Future[WriteResult] = {
    collection.flatMap(
      _.insert(ordered = false)
        .one(horse.copy())
    )
  }

  def update(id: BSONObjectID, horse: Horse): Future[WriteResult] = {

    collection.flatMap(
      _.update(ordered = false).one(BSONDocument("_id" -> id), horse.copy())
    )
  }

  def delete(id: BSONObjectID): Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }

}
