package com.smash.media

import groovy.xml.StreamingMarkupBuilder

class YoutubeVideoTagLib {
    static namespace = "youtube"
    static defaultEncodeAs = 'html'

    def video = { attrs ->
        def videoKey = attrs['videoKey']
        def start = attrs['start']
        def end = attrs['end']
        def urlEnding = "?rel=0" + (start ? "&start=$start" : "") + (end ? "&end=$end" : "")

        if (!videoKey) {
            throw new IllegalArgumentException("Video key cannot be null")
        }

        def vd = {
            iframe(
                    width: attrs['width'] ?: "700",
                    height: attrs['height'] ?: "410",
                    src: "//www.youtube-nocookie.com/embed/${videoKey}${urlEnding}",
                    frameborder: "0",
                    allowfullscreen: null
            )
        }

        def xml = new StreamingMarkupBuilder().bind(vd)
        out << xml
    }
}
