<%@ page import="com.smash.media.Image" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Edit Media Cut</title>
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
    <div class="page-header">
    <a href="${createLink(controller: 'mediaCut', action: 'list', params: [userOnly: true])}"
       class="btn btn-primary">&laquo; Back to list</a>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">Media Cut metadata update</div>

        <div class="panel-body">
            <div id="error" class="alert alert-danger" style="display: none"></div>
            <g:if test="${flash.message}">
                <div class="alert alert-warning">
                    <li>${flash.message}</li>
                </div>
            </g:if>
            <g:if test="${mediaCut?.hasErrors()}">
                <div class="alert alert-warning">
                    <g:eachError bean="${mediaCut}">
                        <li><g:message error="${it}"/></li>
                    </g:eachError>
                </div>
            </g:if>
            <div class="" style="width: 90%; margin: auto">
                <form method="POST"
                      id="editMediaCutForm"
                      action="${createLink(controller: 'mediaCut', action: 'edit', params: [id: mediaCut.id])}">
                    <fieldset>

                        <div class="form-group">
                            <label for="inputTitle">Title</label>
                            <input type="text"
                                   name="title"
                                   class="form-control"
                                   id="inputTitle"
                                   placeholder="Enter the title of your post"
                                   autofocus="true"
                                   required="true"
                                   value="${mediaCut?.title}"
                                   ${(mediaCut.owner == user) ? "" : "disabled"}>
                        </div>

                        <div class="form-group">
                            <label for="inputDescription">Description</label>
                            <textarea id="inputDescription"
                                      class="form-control"
                                      rows="3"
                                      name="description"
                                      placeholder="Describe the point of your post"
                                      style="resize: none"
                                      ${(mediaCut.owner == user) ? "" : "disabled"}>${mediaCut?.description}</textarea>
                        </div>

                        <g:if test="${mediaCut.owner == user}">
                            <button type="submit" class="btn btn-primary">Save &raquo;</button>
                        </g:if>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
