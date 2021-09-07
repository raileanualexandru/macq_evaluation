package repositories

import models.User
import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import org.joda.time.DateTime
import reactivemongo.api.commands.WriteResult

@Singleton
class UserRepository @Inject() (implicit
    executionContext: ExecutionContext,
    reactiveMongoApi: ReactiveMongoApi
) {
  def collection: Future[BSONCollection] =
    reactiveMongoApi.database.map(db => db.collection("users"))

  
  def create(user: User): Future[WriteResult] = {
    collection.flatMap(
      _.insert(ordered = false)
        .one(user.copy())
    )
  }


}