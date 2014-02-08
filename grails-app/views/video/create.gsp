<%@ page import="com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>New Media Cut</title>
</head>

<body>

<div id="container" class="container-fluid" style="width: 98%; margin: 0 auto;">
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <g:link uri="/" class="navbar-brand">Smash</g:link>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${createLink(controller: 'mediaCut')}">Media Cuts</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <p class="navbar-text"><sec:username/></p>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="${createLink(controller: 'userManagement', action: 'update')}">Update</a></li>
                        <li class="divider"></li>
                        <li><a href="${createLink(controller: 'mediaCut', action: 'list', params: [userOnly: 'true'])}">My Media Cuts</a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="${createLink(controller: 'logout')}">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <g:message code="smash.Sign-out"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container" style="width: 50%">
    <div class="panel panel-primary">
        <div class="panel-heading">Create a new video snippet</div>

        <div class="panel-body">
            <div id="error" class="alert alert-danger" style="display: none"></div>
            <g:if test="${flash.message}">
                <div class="alert alert-warning">
                    <li>${flash.message}</li>
                </div>
            </g:if>
            <g:if test="${video?.hasErrors()}">
                <div class="alert alert-warning">
                    <g:eachError bean="${video}">
                        <li><g:message error="${it}"/></li>
                    </g:eachError>
                </div>
            </g:if>
            <div class="" style="width: 90%; margin: auto">
                <form method="POST" id="createVideoForm" action="${createLink(controller: 'video', action: 'create')}">
                    <fieldset>
                        <input type="text" name="videoKey" id="inputId" hidden="hidden">

                        <div class="form-group">
                            <label for="inputTitle">Title</label>
                            <input type="text"
                                   name="title"
                                   class="form-control"
                                   id="inputTitle"
                                   placeholder="Enter the title of your post"
                                   autofocus="true"
                                   required="true"
                                   value="${video?.title}">
                        </div>

                        <div class="form-group">
                            <label for="inputDescription">Description</label>
                            <textarea id="inputDescription"
                                      class="form-control"
                                      rows="3"
                                      name="description"
                                      placeholder="Describe the point of your post"
                                      style="resize: none">${video?.description}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="inputUrl">URL</label>
                            <input type="url" class="form-control" id="inputUrl"
                                   placeholder="Enter the youtube video URL"
                                   required="true"
                                   value="${video?.videoKey ? "http://www.youtube.com/watch?v=" + video.videoKey : ""}">
                        </div>

                        <div class="form-group">
                            <label class="control-label">From</label>

                            <div>
                                <g:select class="input-sm" name="start_hr" from="${0..23}"/> <strong>:</strong>
                                <g:select class="input-sm" name="start_min" from="${0..59}"/> <strong>:</strong>
                                <g:select class="input-sm" name="start_sec" from="${0..59}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label">To</label>

                            <div>
                                <g:select class="input-sm" name="end_hr" from="${0..23}"/> <strong>:</strong>
                                <g:select class="input-sm" name="end_min" from="${0..59}"/> <strong>:</strong>
                                <g:select class="input-sm" name="end_sec" from="${0..59}"/>
                            </div>
                            <span class="help-block">Maximum video duration: ${Video.MAXIMUM_DURATION}s</span>
                        </div>

                        <button type="submit" class="btn btn-primary">Save &raquo;</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    var errorField = $('#error');

    $(document).ready(function () {
        errorField.html("");
        errorField.hide();
    });

    $("#createVideoForm").submit(function () {
        var urlRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
        var url = $('#inputUrl').val();

        // check url
        if (url.match(urlRegex)) {
            var id = RegExp.$1
            $("#inputId").val(id);
        } else {
            errorField.html("The URL you submitted is not a valid youtube URL");
            errorField.show();
            $("#inputUrl").focus();
            return false;
        }

        return true;
    });
</script>

</body>
</html>
