package com.smash.controllers

import grails.plugin.springsecurity.SpringSecurityService;
import grails.plugin.springsecurity.annotation.Secured;
import grails.test.mixin.Mock;

import com.smash.domain.MediaCut;
import com.smash.domain.MediaType;
import com.smash.domain.Tag;
import com.smash.mediaCut.MediaCutService;
import com.smash.mediaCut.TagService;
import com.smash.user.Role;

@Mock([Role])
class MediaCutController {
	static allowedMethods = [create:'POST'];
	MediaCutService mediaCutService;
	SpringSecurityService springSecurityService;
	TagService tagService;
	
	@Secured(['IS_AUTHENTICATED_FULLY'])
    def index() { 
		render(view: "add");
	}
	
	@Secured(['IS_AUTHENTICATED_FULLY'])
	def create() {
		String title = params.title;
		String description = params.description;
		String tags = params.tags;
		String link = params.link;
		int type = params.type.toInteger();
		
		if(title == null || title.empty) {
			flash.message = message(code: 'smash.mediaCut.missingTitle');
			redirect action: 'index';
			return;
		}
		if(description == null || description.empty) {
			flash.message = message(code: 'smash.mediaCut.missingDescription');
			redirect action: 'index';
			return;
		}
		if(link == null || link.empty) {
			flash.message = message(code: 'smash.mediaCut.missingLink');
			redirect action: 'index';
			return;
		}
		
		List<Tag> tagList = new ArrayList<Tag>();
		if(tags != null && !tags.empty) {
			for (tag in tags.split(" ")) {
				tagList.add(tagService.createTag(new Tag(name: tag)));
			}
		}
		
		MediaType mediaType = MediaType.containValue(type);
		if(mediaType == null) {
			flash.message = message(code: 'smash.mediaCut.badType');
			redirect action: 'index';
			return;
		}
		else {
			MediaCut media = new MediaCut(name:title, description: description, type: mediaType, fileName: link);
			mediaCutService.create(media);
			flash.message = message(code: 'smash.mediaCut.added');
			redirect uri: '/';
			return;
		}
	}
}
