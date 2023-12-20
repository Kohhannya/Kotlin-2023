package _practice.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import _practice.api.model.CreateCommentRequest
import _practice.repository.CommentsRepository
import org.koin.ktor.ext.inject

fun Application.commentsApi() {
    routing {

        val commentsRepository by inject<CommentsRepository>()

        get("/comments/all") {
            println("Ищем все комменты")
            val comments = commentsRepository.getAll()
            call.respond(comments)
        }
        get("/comments/{id}") {
            val commentId = call.parameters["id"]?.toLong() ?: 0
            val comment = commentsRepository.getById(commentId)

            call.respond(comment ?: HttpStatusCode.NotFound) }

        put("/comments/create") {
            val request = call.receive<CreateCommentRequest>()

            val createdComment = commentsRepository.createComment(request.commentText)

            call.respond(createdComment)
        }
    }
}
