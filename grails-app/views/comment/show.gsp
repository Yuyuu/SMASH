<%@ page import="com.smash.domain.Comment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Smash</title>
</head>

<body>
<div id="container" class="container-fluid" style="width: 98%; margin: 0 auto;">
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Smash</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <sec:ifLoggedIn>
                    <p class="navbar-text"><sec:username/></p>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${createLink(controller: 'userManagement', action: 'update')}">Update</a></li>
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

<div class="container" style="width: 100%">
    <div class="jumbotron" style="text-align: center;">
        <h1>SMASH</h1>

        <p>Short Media Sharing</p>
    </div>
</div>

<div class="container" style="width: 70%; margin: 0 auto;">
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="['Comment']"/></g:link></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="['Comment']"/></g:link></li>
        </ul>
    </div>

    <div class="row" style="margin-bottom: 5em; ">
        <div class="col-lg-6" style="text-align: center;">
            <div id="show-comment" class="content scaffold-show" role="main">
                <h1><g:message code="default.show.label" args="['Comment']"/></h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <ol class="property-list comment">

                    <g:if test="${commentInstance?.text}">
                        <li class="fieldcontain">
                            <span id="text-label" class="property-label"><g:message
                                                                                    default="Text"/></span>

                            <span class="property-value" aria-labelledby="text-label"><g:fieldValue bean="${commentInstance}"
                                                                                                    field="text"/></span>

                        </li>
                    </g:if>

                    <g:if test="${commentInstance?.author.username}">
                        <li class="fieldcontain">
                            <span id="author-label" class="property-label"><g:message default="Author"/></span>

                            <span class="property-value" aria-labelledby="author-label"><g:link controller="user" action="show"
                                                                                                id="${commentInstance?.author?.id}">${commentInstance?.author?.username.encodeAsHTML()}</g:link></span>

                        </li>
                    </g:if>

                    <g:if test="${commentInstance?.media.name}">
                        <li class="fieldcontain">
                            <span id="media-label" class="property-label"><g:message
                                                                                     default="Media"/></span>

                            <span class="property-value" aria-labelledby="media-label"><g:link controller="mediaCut" action="show"
                                                                                               id="${commentInstance?.media?.id}">${commentInstance?.media?.name.encodeAsHTML()}</g:link></span>

                        </li>
                    </g:if>

                </ol>
                <g:form url="[resource: commentInstance, action: 'delete']" method="DELETE">
                    <fieldset class="buttons">
                        <g:actionSubmit class="delete" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
