package org.kohhannya.repository

import org.kohhannya.model.Comment

interface CommentsRepository {

    fun getAll(): Collection<Comment>

    fun getById(id: Long): Comment?

    fun createComment(commentText: String): Comment
}