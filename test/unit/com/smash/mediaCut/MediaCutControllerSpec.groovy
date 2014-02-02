




package com.smash.mediaCut

import com.smash.controllers.MediaCutController;
import com.smash.domain.MediaCut;
import com.smash.domain.Tag;
import com.smash.mediaCut.MediaCutService;
import com.smash.mediaCut.TagService;
import com.smash.user.Role;
import com.smash.user.User

import grails.plugin.springsecurity.SpringSecurityService;
import grails.test.mixin.Mock;
import grails.test.mixin.TestFor
import groovy.mock.interceptor.MockFor;
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MediaCutController)
class MediaCutControllerSpec extends Specification {
	SpringSecurityService springSecurityService;
	MediaCutService mediaCutService;
	TagService tagService;
	User user;
	
	
    def setup() {
		user = new User(username:"name", email:"email", password:"password");
		
		mediaCutService = Mock(MediaCutService);
		controller.mediaCutService = mediaCutService
		
		tagService = Mock(TagService);
		controller.tagService = tagService;
		
		springSecurityService = Mock(SpringSecurityService);
		springSecurityService.currentUser >> user;
		controller.springSecurityService = springSecurityService;
    }
	
	def testIndex() {
		given: "nothing"
		when: "the index action is called"
			controller.index();
		then: "the main page is loaded"
			view == "/mediaCut/add";
	}
	
	def "test create when empty field"() {
		given: "form values"
			params.title = title;
			params.description = description;
			params.link = link;
			params.type = "1";	//we don't test this value here
		
		when: "create action is called"
			controller.create();
		
		then: "the MediaCutService should not be called"
			0 * mediaCutService.create((MediaCut) _);
			
		and: "we should be redirect to the form"
			response.redirectUrl == "/mediaCut/index";
			
		and: "a message should be shown"
			flash.message
			
		where:
			title	| description	| link
			null	| "descr"		| "link"
			""		| "descr"		| "link"
			"title"	| null			| "link"
			"title"	| ""			| "link"
			"title"	| "descr"		| null
			"title"	| "descr"		| ""
	}
	
	def "test create when bad media type value"() {
		given: "form values"
			params.title = "title";
			params.description = "description";
			params.link = "link";
			params.type = type;
		
		when: "create action is called"
			controller.create();
			
		then: "MediaCutService should not be called"
			0 * mediaCutService.create((MediaCut) _);
		
		and: "we should be redirect to the form"
			response.redirectUrl == "/mediaCut/index";
		
		and: "a message should be shown"
			flash.message
			
		where:
			type << [-1, -2, 0, 6, 7, 8];
	}
	
	def "test create success" () {
		given: "form values"
			params.title = title;
			params.description = description;
			params.tags = tags;
			params.link = link;
			params.type = type;
		
		when: "create action is called"
			controller.create();
			
		then: "MediaCutService should be called"
			1 * mediaCutService.create((MediaCut) _);
		
		and: "TagService shoulb be called"
			expectedTagNumber * tagService.createTag((Tag)_);
			
		and: "we should be redirect to the main page"
			response.redirectUrl == "/";
			
		where:
			title	| description	| tags			| link		| type	| expectedTagNumber
			"title"	| "description"	| ""			| "link"	| 1		| 0
			"title"	| "descr"		| "tag1"		| "link"	| 2		| 1
			"title"	| "descr"		| "tag1 tag2"	| "link"	| 3		| 2
	}

    def cleanup() {
    }

    void "test something"() {
    }
}
