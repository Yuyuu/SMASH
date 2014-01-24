<%@ page import="com.smash.domain.Comment" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-comment" class="skip" tabindex="-1"><g:message
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-comment" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <th><g:message code="comment.author.label" default="Author"/></th>

            <th><g:message code="comment.media.label" default="Media"/></th>

            <g:sortableColumn property="text" title="${message(code: 'comment.text.label', default: 'Text')}"/>


        </tr>
        </thead>
        <tbody>
        <g:each in="${Comment.getAll()}" status="i" var="commentInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${commentInstance}">${fieldValue(bean: commentInstance, field: "text")}</g:link></td>

                <td>${fieldValue(bean: commentInstance, field: "author")}</td>

                <td>${fieldValue(bean: commentInstance, field: "media")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${commentInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
