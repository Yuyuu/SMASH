package com.smash.media

import groovy.transform.ToString

@ToString
class Video extends MediaCut {

    public static Long MAXIMUM_DURATION = 30

    String videoKey
    Long startTime
    Long endTime

    static constraints = {
        videoKey nullable: false, blank: false
        startTime nullable: false
        endTime validator: { val, video ->
            return (val != 0) && (val - video.startTime <= MAXIMUM_DURATION)
        }
    }
}
