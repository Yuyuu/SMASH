<!DOCTYPE html>
<html>
<head>
    <resource:richTextEditor type="medium" />
    <meta name="layout" content="smash">
    <g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}"/>
    <title><g:message code="default.create.label" args="['Comment']"/></title>
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

<a href="#create-comment" class="skip" tabindex="-1"><g:message
                                                                default="Skip to content&hellip;"/></a>
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
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        </ul>
    </div>

    <div id="create-comment" class="content scaffold-create" role="main">
        <h2><g:message code="default.create.label" args="[entityName]"/></h2>
        <g:form url="[resource: commentInstance, action: 'save']">
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save"
                                value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            </fieldset>
        </g:form>
    </div>
  </div>
</body>
</html>
