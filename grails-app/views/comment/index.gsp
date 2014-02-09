<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width: 50%;">
    <div class="panel panel-primary">
        <div class="panel-heading">Show comments</div>
        <div class="fieldcontain ${hasErrors(bean: media, field: 'error')} required">
            <g:each var="comment" in="${MediaCut.findById(media).comments}">
                <g:include action="show" controller="comment" params="[commentId:comment.id]"></g:include>
            </g:each>
        </div>
    </div>
</div>

</body>
</html>

