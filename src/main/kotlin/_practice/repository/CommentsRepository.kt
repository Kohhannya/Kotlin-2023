package _practice.repository

import _practice.model.Comment

interface CommentsRepository {

    fun getAll(): Collection<Comment>

    fun getById(id: Long): Comment?

    fun createComment(commentText: String): Comment
}