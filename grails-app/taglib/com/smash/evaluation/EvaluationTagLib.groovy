package com.smash.evaluation

class EvaluationTagLib {

    def ifAlreadyVoted = { attrs, body ->
        def evaluations = attrs.evaluations
        def loggedUserId = attrs.loggedUserId
        if(evaluations.find{it.owner.id == Long.parseLong(loggedUserId.toString())}) {
            out << body() << "<span class='hidden'>OK</span>"
        }
    }

    def ifNotAlreadyVoted = { attrs, body ->
        def evaluations = attrs.evaluations
        def loggedUserId = attrs.loggedUserId
        if(!evaluations.find{it.owner.id == Long.parseLong(loggedUserId.toString())}) {
            out << body() << "<span class='hidden'>OK</span>"
        }
    }
}
