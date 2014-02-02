package com.smash.mediaCut

import com.smash.domain.MediaCut;
import com.smash.domain.MediaType
import com.smash.domain.Tag;

import grails.transaction.Transactional

import org.springframework.web.multipart.MultipartFile

/**
 * @author SERIN Kevin
 *
 */
@Transactional
class MediaCutService {
	
	def create(MediaCut media) {
		media.save();
		return media;
	}
	
	def delete(MediaCut media) {
		media.delete();
	}
}
