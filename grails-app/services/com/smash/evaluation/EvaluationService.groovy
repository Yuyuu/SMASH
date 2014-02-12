package com.smash.evaluation

import com.smash.domain.Evaluation
import com.smash.media.MediaCut
import grails.transaction.Transactional

@Transactional
class EvaluationService {

    def voteForMediaCut(MediaCut mediacut, Evaluation evaluation) {
        if(evaluation.getVote())
            mediacut.setNbPositiveVote(++mediacut.getNbPositiveVote())
        else
            mediacut.setNbNegativeVote(++mediacut.getNbNegativeVote())
        mediacut.addToEvaluations(evaluation)
        mediacut.save(flush: true)
    }
}
