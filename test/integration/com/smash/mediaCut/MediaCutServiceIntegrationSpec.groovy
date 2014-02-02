package com.smash.mediaCut

import com.smash.domain.MediaCut;
import com.smash.domain.MediaType;
import com.smash.domain.Tag
import com.smash.mediaCut.MediaCutService;

import spock.lang.*


class MediaCutServiceIntegrationSpec extends Specification {
	MediaCutService mediaCutService;
	MediaCut existingMedia
	
	def setup() {
		def tags = [new Tag(name:"tag")];
		existingMedia = new MediaCut(name: "existingMedia", fileName:"name", description:"descr", type:MediaType.FILM, tags:tags);
		existingMedia.save(failOnError:true);
	}
	
    def "create new media test"() {
		given: "media type information"
			def name = "my media";
			def description = "description";
			def mediaType = MediaType.TV;
			def fileName = "path/my_media_1.mpeg";
			def tags = [new Tag(name:"tag1"), new Tag(name:"tag2")];
			def media = new MediaCut(name:name, description:description, type:mediaType, fileName:fileName, tags:tags);
		
		when: "create the media"
			mediaCutService.create(media);
			
		then: "media and tags are added"
			MediaCut inserted = MediaCut.findByName("my media");
			inserted != null;
			inserted.name == name;
			inserted.description == description;
			inserted.type == mediaType;
			inserted.fileName == fileName;
			inserted.tags.size() == 2;
	}
	
	def "delete media test"() {
		given: "an existing media cut"
			MediaCut media = existingMedia;
		
		when: "removing the media cut"
			mediaCutService.delete(media);
			
		then: "The media does not exist anymore"
			MediaCut.get(media.id) == null;
		
		and: "The associated tags are NOT removed"
			Tag.findByName("tag") != null;
	}
}
