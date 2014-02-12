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
        endTime nullable: false, validator: { val, video ->
            if (val == 0) {
                return false
            }
            if (val - video.startTime > MAXIMUM_DURATION) {
                return ["smash.video.duration.too-long", MAXIMUM_DURATION]
            }
        }
    }
}
