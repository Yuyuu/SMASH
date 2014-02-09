<%@ page import="com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Media Cuts</title>
</head>

<body>

<div id="container" class="container-fluid" style="width: 98%; margin: 0 auto;">
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <g:link uri="/" class="navbar-brand">Smash</g:link>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <g:if test="${!userOnly}">
                    <li class="active">
                        <a href="${createLink(controller: 'mediaCut')}">Media Cuts</a>
                    </li>
                </g:if>
                <g:else>
                    <li>
                        <a href="${createLink(controller: 'mediaCut')}">Media Cuts</a>
                    </li>
                </g:else>
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
    <div class="row">
        <g:if test="${flash.message}">
            <div class="alert alert-danger">${flash.message}</div>
        </g:if>
        <div class="col-md-6">
            <g:if test="${userOnly}">
                <h3><sec:username/>'s Media Cuts</h3>
            </g:if>
            <g:else>
                <h3>Media Cuts</h3>
            </g:else>
        </div>
        <div class="col-md-6 text-right">
            <div class="btn-group" style="margin-top: 2%">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                    Create Media Cut <span class="caret"></span>
                </button>
                <ul class="dropdown-menu text-left" role="menu">
                    <li><a href="${createLink(controller: 'video', action: 'create')}">Video</a></li>
                    <li><a href="#">Image</a></li>
                </ul>
            </div>
        </div>
    </div>

    <g:each in="${mediacutRepresentationList}" var="mediacut">
        <div class="well well-sm">
            <div class="row">
                <div class="col-md-6">
                    <span class="label label-primary">${mediacut.class.simpleName}</span>
                </div>
                <g:if test="${!userOnly}">
                    <div class="col-md-6 text-right">
                        <strong><small>${mediacut.owner.username}</small></strong>
                    </div>
                </g:if>
            </div>
            <a href="${createLink(controller: mediacut.class.simpleName.toLowerCase(), action: 'show', params: [id: mediacut.id])}">
                <h4>${mediacut.title}</h4>
            </a>
            <p>Description: ${mediacut.description}</p>
            <g:if test="${mediacut.class == Video}">
                <p>Duration: ${((Video) mediacut).endTime - ((Video) mediacut).startTime}s</p>
            </g:if>
            <g:if test="${mediacut.comments}">
                <p>Comments: ${mediacut.comments.size()}</p>
            </g:if>
        </div>
    </g:each>
</div>

</body>
</html>
