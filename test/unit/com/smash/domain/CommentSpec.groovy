package com.smash.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
class CommentSpec extends Specification {

    @Unroll
    void "dummyTest"() {
        expect:
        res == a + b

        where:
        a  | b  | res
        1  | 1  | 2
        2  | 3  | 5
        23 | 10 | 33
    }
}
