package com.smash.domain

import com.smash.media.Image
import com.smash.media.Video
import com.smash.user.User
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Evaluation)
class EvaluationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    def "test evaluation constraints"() {

        setup: "when a new evaluation is created"
            Evaluation evaluation = new Evaluation(vote: vote, owner: owner, media: mediacut)

        expect: "the validation gives..."
            evaluation.validate() == valid

        where:
            vote  | owner      | mediacut    | valid
            true  | new User() | new Video() | true
            false | new User() | new Video() | true
            true  | null       | new Video() | false
            false | null       | new Video() | false
            true  | new User() | null        | false
            false | new User() | null        | false
            true  | null       | null        | false
            false | null       | null        | false
            null  | new User() | new Video() | false
            null  | null       | new Video() | false
            null  | new User() | null        | false
            null  | null       | null        | false
    }
}
