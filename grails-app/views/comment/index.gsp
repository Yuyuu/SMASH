<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width: 60%;">
    <g:if test='${flash.message}'>
        <div class='alert alert-danger alert-dismissable' style="text-align: left">
            ${flash.message}
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        </div>
    </g:if>
    <div class="fieldcontain ${hasErrors(bean: media, field: 'error')} required">
        <g:each var="comment" in="${MediaCut.findById(media).comments}">
            <g:include action="show" controller="comment" params="[commentId:comment.id]"></g:include>
        </g:each>
    </div>
</div>

</body>
</html>

