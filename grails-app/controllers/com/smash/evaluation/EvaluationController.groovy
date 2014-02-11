package com.smash.evaluation

import com.smash.domain.Evaluation
import com.smash.media.Image
import com.smash.media.MediaCut
import com.smash.media.Video
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class EvaluationController {

    EvaluationService evaluationService
    SpringSecurityService springSecurityService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def vote() {
        MediaCut mediacut
        if(params.type == "image")
            mediacut = Image.get(params.id)
        else
            mediacut = Video.get(params.id)

        def evaluation = new Evaluation(owner: springSecurityService.getCurrentUser(),
                vote: Boolean.parseBoolean(params.vote).booleanValue(),media: mediacut as MediaCut)
        evaluationService.voteForMediaCut(mediacut, evaluation)
        redirect(controller: mediacut.getClass().getSimpleName(), action: "show", id: params.id)
    }
}
