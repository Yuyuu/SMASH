package com.smash.media

class Image extends MediaCut {

    public static Integer MAXIMUM_SIZE = 1024 * 1024 * 1 // 1 Mb

    byte[] blob
    String mimeType
    String fileName

    static constraints = {
        blob maxSize: MAXIMUM_SIZE
        mimeType nullable: false, blank: false, matches: '^image/(png|jpeg)$'
        fileName nullable: false, blank: false
    }
}
