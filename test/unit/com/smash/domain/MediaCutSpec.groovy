package com.smash.domain

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MediaCut)
class MediaCutSpec extends Specification {

    def "test getValue"() {
		given: "A media type" 
			MediaType enumValue = currentEnum;
			
		when: "getValue is called"
			def result = enumValue.getValue();
			
		then: "test the expected value"
			result == expected;
			
		where:
			currentEnum		| expected
			MediaType.FILM	| 1
			MediaType.MOVIE	| 2
			MediaType.MUSIC	| 3
			MediaType.RADIO	| 4
			MediaType.TV	| 5
    }

	def "test containValue()"() {
		given: "an integer"
			int val = currentValue;
		
		when: "containsValue is called"
			def result = MediaType.containValue(val);
			
		then:
			(expected && result != null) || (!expected && result == null)
			
		where:
			currentValue	| expected
			0				| false
			1				| true
			2				| true
			3				| true
			4				| true
			5				| true
			6				| false
			7				| false
			8				| false
	}
}
