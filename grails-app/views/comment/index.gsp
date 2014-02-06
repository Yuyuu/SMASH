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
            <li><g:link class="list" action="create"><g:message code="default.create.label" args="['Comment']"/></g:link></li>
        </ul>
    </div>
    <div class="row" style="margin-bottom: 5em; ">
        <div class="col-lg-6" style="text-align: center;">

            <div id="list-comment" class="content scaffold-list" role="main">
             <%--   <h1><g:message code="default.list.label" args="[entityName]"/></h1> --%>
                <table>
                    <thead>
                    <tr>
                        <th><g:message code="comment.author.label" default="Author"/></th>
                        <th><g:message code="comment.media.label" default="Media"/></th>
                        <g:sortableColumn property="text" title="${message(code: 'comment.text.label', default: 'Comment')}"/>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${!Comment.list().equals(null)}">
                        <g:each in="${Comment.list()}" status="i" var="commentInstance">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                              <%--  <td>${fieldValue(bean: commentInstance, field: "author")}</td>
                                <td>${fieldValue(bean: commentInstance, field: "media")}</td> --%>
                                <td>${commentInstance?.getAuthorUsername()}</td>
                                <td>${commentInstance?.getMediaName()}</td>
                                <td><g:link action="show"
                                            id="${commentInstance?.id}">${fieldValue(bean: commentInstance, field: "text")}</g:link></td>
                            </tr>
                        </g:each>
                    </g:if>
                    </tbody>
                </table>
                <div class="pagination">
                    <g:paginate total="${Comment.list().size() ?: 0}"/>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
