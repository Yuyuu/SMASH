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
        <sec:ifLoggedIn>
            <div class="col-md-6 text-right">
                <div class="btn-group" style="margin-top: 2%">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        Create Media Cut <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu text-left" role="menu">
                        <li><a href="${createLink(controller: 'video', action: 'create')}">Video</a></li>
                        <li><a href="${createLink(controller: 'image', action: 'create')}">Image</a></li>
                    </ul>
                </div>
            </div>
        </sec:ifLoggedIn>
    </div>

    <g:each in="${mediacutRepresentationList}" var="mediacut">
        <div class="well well-sm mediacut-thumbnail">
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
            <g:if test="${mediacut.owner == user}">
                <div class="pull-right btn-mediacuts-action">
                    <div class="btn-group">
                        <button id="btn-delete-mediacut"
                           data-toggle="modal"
                           data-target="#deleteMediaCutModal"
                           class="btn btn-default btn-xs"
                           onclick="$('#delete-modal-id').val('${mediacut.id}');
                           $('#myModalLabel').text('Delete Media Cut: ${mediacut.title}')">
                            Delete
                        </button>
                    </div>
                </div>
            </g:if>
            <a href="${createLink(controller: mediacut.class.simpleName.toLowerCase(), action: 'show', params: [id: mediacut.id])}">
                <h4>${mediacut.title}</h4>
            </a>
            <p>Description: ${mediacut.description}</p>
            <g:if test="${mediacut.class == Video}">
                <p>Duration: ${((Video) mediacut).endTime - ((Video) mediacut).startTime}s</p>
            </g:if>
        </div>
    </g:each>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteMediaCutModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    <g:form controller="mediaCut" action="delete" style="margin: 0">
        <div class="modal-content">
            <input type="hidden" name="id" id="delete-modal-id"/>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Delete Media Cut</h4>
            </div>
            <div class="modal-body">
                This operation cannot be undone.<br>Are you sure you want to delete this Media Cut ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-danger">Delete</button>
            </div>
        </div>
        </g:form>
    </div>
</div>

</body>
</html>
