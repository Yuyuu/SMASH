package com.smash.media

class Image extends MediaCut {

    public static Integer MAXIMUM_SIZE = 1024 * 1024 * 1 // 1 MB

    byte[] dataBlob
    String mimeType
    String fileName

    static constraints = {
        dataBlob maxSize: MAXIMUM_SIZE
        mimeType nullable: false, blank: false, matches: '^image/(png|jpeg)$'
        fileName nullable: false, blank: false
    }
}
