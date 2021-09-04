package repositories

import  javax.inject.inject
import scala.concurrent.ExecutionContext
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.Future
import reactivemongo.play.json.collection.JSONCollection
import models.Post
import reactivemongo.bson.BSONDocument
import reactivemongo.api.ReadPreference

class PostRepository @inject () (
    implicit ec: ExecutionContext,
    reactiveMongoApi: ReactiveMongoApi
) {
    private def collection: Future[JSONCollection]=
        reactiveMongoApi.database.map(_.collection("posts"))

        def list(limit: Int = 100): Future[Seq[Post]] = 
            collection.flatMap(_
            .find(BSONDocument())
            .cursor[Post](ReadPreference.primary)
            .collect[Seq](limit, cursor.FailOnError[Seq[Post]]())
            )

        def create

        def read

        def update

        def destroy
}