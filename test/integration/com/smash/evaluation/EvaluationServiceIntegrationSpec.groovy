package com.smash.evaluation

import com.smash.BootstrapTestService
import com.smash.domain.Evaluation
import com.smash.media.Image
import com.smash.media.MediaCut
import com.smash.media.Video
import com.smash.user.User
import spock.lang.*

/**
 *
 */
class EvaluationServiceIntegrationSpec extends Specification {
    EvaluationService evaluationService
    BootstrapTestService bootstrapTestService

    def setup() {
        bootstrapTestService.initializeTests()
    }

    def cleanup() {
    }

    void "test number of positive votes is well incremented for a Video"() {
        given : "a Video and a positive Evaluation"
            MediaCut mediacut = new Video(title: "Une video", owner: User.findByUsername("owner"), videoKey: "key",
                    startTime: 0, endTime: 10, dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: true, owner: User.findByUsername("owner2"), media: mediacut)
            def nbPositiveVote = mediacut.getNbPositiveVote()

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that nbPositiveVote is incremented"
            mediacut.getNbPositiveVote() == ++nbPositiveVote
    }

    void "test number of positive votes is well incremented for an Image"() {
        given : "an Image and a positive Evaluation"
            MediaCut mediacut = new Image(title: "Une image", owner: User.findByUsername("owner"),
                    blob: new byte[10], mimeType: "image/png", fileName: "my image", dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: true, owner: User.findByUsername("owner2"), media: mediacut)
            def nbPositiveVote = mediacut.getNbPositiveVote()

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that nbPositiveVote is incremented"
            mediacut.getNbPositiveVote() == ++nbPositiveVote
    }

    void "test number of negative votes is well incremented for a Video"() {
        given : "a Video and a negative Evaluation"
            MediaCut mediacut = new Video(title: "Une video", owner: User.findByUsername("owner"), videoKey: "key",
                    startTime: 0, endTime: 10, dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)
            def nbNegativeVote = mediacut.getNbNegativeVote()

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that nbPositiveVote is incremented"
            mediacut.getNbNegativeVote() == ++nbNegativeVote
    }

    void "test number of negative votes is well incremented for an Image"() {
        given : "an Image and a negative Evaluation"
            MediaCut mediacut = new Image(title: "Une image", owner: User.findByUsername("owner"),
                    blob: new byte[10], mimeType: "image/png", fileName: "my image", dateCreated: new Date())
        Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)
            def nbNegativeVote = mediacut.getNbNegativeVote()

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that nbPositiveVote is incremented"
            mediacut.getNbNegativeVote() == ++nbNegativeVote
    }

    void "test evaluation is added correctly in the Video"() {
        given : "a Video and an Evaluation"
            MediaCut mediacut = new Video(title: "Une video", owner: User.findByUsername("owner"), videoKey: "key",
                    startTime: 0, endTime: 10, dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that the evaluation is well inserted in the Video's list containing all its evaluations"
            mediacut.getEvaluations().contains(evaluation)
    }

    void "test evaluation is added correctly in the Image"() {
        given : "an Image and an Evaluation"
            MediaCut mediacut = new Image(title: "Une image", owner: User.findByUsername("owner"),
                    blob: new byte[10], mimeType: "image/png", fileName: "my image", dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)

        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that the evaluation is well inserted in the Video's list containing all its evaluations"
            mediacut.getEvaluations().contains(evaluation)
    }

    void "test video and evaluation are saved correctly"() {
        given : "a Video and an Evaluation"
            MediaCut mediacut = new Video(title: "Une video", owner: User.findByUsername("owner"), videoKey: "key",
                    startTime: 0, endTime: 10, dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)
        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that the video and the evaluation on the video are saved in the database"
            MediaCut.find(mediacut) && Evaluation.find(evaluation)
    }

    void "test image and evaluation are saved correctly"() {
        given : "an Image and an Evaluation"
            MediaCut mediacut = new Image(title: "Une image", owner: User.findByUsername("owner"),
                    blob: new byte[10], mimeType: "image/png", fileName: "my image", dateCreated: new Date())
            Evaluation evaluation = new Evaluation(vote: false, owner: User.findByUsername("owner2"), media: mediacut)
        when : "service is called"
            evaluationService.voteForMediaCut(mediacut, evaluation)

        then : "expect that the image and the evaluation on the image are saved in the database"
            MediaCut.find(mediacut) && Evaluation.find(evaluation)
    }
}
