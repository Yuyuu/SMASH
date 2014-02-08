package com.smash.tag

import com.smash.domain.Tag
import grails.transaction.Transactional

@Transactional
class TagService {

	/**
	 * Creates a new tag.
	 * @param tag The tag to create
	 * @return The created tag or a tag with the same name already persisted
	 */
	def createTag(Tag tag) {
		Tag existedTag = Tag.findByName(tag.name);
		if(existedTag == null) {
			tag.save();
			return tag;
		}
		else {
			return existedTag;
		}
	}

	/**
	 * Removes a tag
	 * @param tag tag to remove
	 */
	def deleteTag(Tag tag) {
		tag.delete();
	}
}
