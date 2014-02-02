package com.smash.mediaCut

import com.smash.domain.Tag
import com.smash.mediaCut.TagService;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
class TagServiceIntegrationSpec extends Specification {
	TagService tagService;
	Tag existingTag;
	
    def setup() {
		existingTag = new Tag(name:"existingTag");
		existingTag.save();
    }

    def "insert tag test"() {
		given: "a tag"
			Tag tag = new Tag(name:"myTag");
		
		when: "add a tag"
			tagService.createTag(tag);
			
		then: "the tag is added"
			Tag inserted = Tag.findByName("myTag");
			inserted != null;
			inserted.name == "myTag";
    }
	
	def "no insertion when tag already exists test"() {
		given: "an existing tag"
			Tag tag = new Tag(name:"existingTag");
			
		when: "add a tag"
			Tag returnedTag = tagService.createTag(tag);
			
		then: "no new tag should not persisted"
			returnedTag != null;
			returnedTag.id == existingTag.id;
			returnedTag.name == existingTag.name;
	}
	
	def "deletion test"() {
		given: "an existing tag"
			Tag tag = existingTag;
			
		when: "deleting a tag"
			tagService.deleteTag(tag);
			
		then: "the tag don't exist anymore"
			Tag.findByName(tag.name) == null;
	}
}
