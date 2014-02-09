<%@ page import="com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>${video.title}</title>
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
                        <li><a href="${createLink(controller: 'mediaCut', action: 'list', params: [userOnly: 'true'])}">My Media Cuts</a></li>
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

<div class="container" style="width: 60%">
    <g:if test='${flash.message}'>
        <div class='alert alert-danger alert-dismissable' style="text-align: left">
            ${flash.message}
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        </div>
    </g:if>
    <a href="${createLink(controller: 'mediaCut', action: 'list', params: [userOnly: true])}" class="btn btn-primary">&laquo; Back to list</a>
    <a href="${createLink(controller: 'comment', action: 'create', params: [media: video.id])}" class="btn btn-primary">&raquo; Add comment</a>
    <g:if test="${video.comments}">
        <a href="${createLink(controller: 'comment', action: 'index', params: [media: video.id])}" class="btn btn-primary">Show comments</a>
    </g:if>
    <h1>${video.title}</h1>
    <blockquote>${video.description}</blockquote>
    <youtube:video videoKey="${video.videoKey}" start="${video.startTime}" end="${video.endTime}" />
</div>
</body>
</html>
