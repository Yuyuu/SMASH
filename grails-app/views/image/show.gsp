<%@ page import="com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>${image.title}</title>
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
                <sec:ifLoggedIn>
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
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li><g:link controller="login"><g:message code="smash.Sign-in"/></g:link></li>
                </sec:ifNotLoggedIn>
            </ul>
        </div>
    </nav>
</div>

<div class="container" style="width: 60%">
    <a href="${createLink(controller: 'mediaCut', action: 'list', params: [userOnly: true])}" class="btn btn-primary">&laquo; Back to list</a>
    <h1>${image.title}</h1>
    <blockquote>${image.description}</blockquote>
    <div class="text-center">
        <img src="${createLink(controller: 'image', action: 'showRaw', params: [id: image.id])}" alt="${image.fileName}" class="img-thumbnail"/>
    </div>
</div>

</body>
</html>
