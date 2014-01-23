package com.smash

import com.smash.domain.Comment
import com.smash.domain.MediaCut
import smash.CommentService

class CommentController {

    def springSecurityService
    def commentService

    def index(){

        render(view:"index")
    }
    def create(){

        // only the current User can add comments to a mediaCut
        def mediaCutList = MediaCut.list()
        [commentInstance: new Comment(params)]
    }

    def save(){

        def commentInstance = commentService.createAndSave(params)
        if (commentInstance.hasErrors()) {
            render(view: "create", model: [commentInstance: commentInstance])
            return
        }

        redirect(action: "show", id:commentInstance.id)
    }

    def show(){

        def commentList = Comment.getAll()
        render(view:"show", [commentInstanceList: commentList])
    }

    def edit(){

        render(view:"edit")
    }
}
