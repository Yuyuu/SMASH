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
    <h1>${video.title}</h1>

    <div class="row">
        <div class="col-sm-10">
            <blockquote>${video.description}</blockquote>
        </div>
        <g:if test="${video.owner == user}">
            <div class="col-md-2">
                <div class="pull-right">
                    <div class="btn-group">
                        <button id="btn-delete-image"
                                data-toggle="modal"
                                data-target="#deleteMediaCutModal"
                                class="btn btn-warning"
                                onclick="$('#delete-modal-id').val('${video.id}');
                                $('#myModalLabel').text('Delete Media Cut: ${video.title}')">
                            Delete this post
                        </button>
                    </div>
                </div>
            </div>
        </g:if>
    </div>

    <div>
        <div class="parent">
            <sec:ifLoggedIn>
                <div class="child">
                    <g:form controller="evaluation" action="vote">
                        <g:ifNotAlreadyVoted evaluations="${video.evaluations}" loggedUserId="${sec.loggedInUserInfo(field: 'id')}" >
                            <span class ="smash">
                                <g:actionSubmit class="vote" value="true" action="vote" />
                                <g:hiddenField name="vote" value="true" />
                                <g:hiddenField name="type" value="video" />
                                <g:hiddenField name="id" value="${video.id}" />
                            </span>
                        </g:ifNotAlreadyVoted>
                        <g:ifAlreadyVoted evaluations="${video.evaluations}" loggedUserId="${sec.loggedInUserInfo(field: 'id')}" >
                            <g:img dir="images" file="smash.png" />
                        </g:ifAlreadyVoted>
                        <span class="nbvote"><p>${video.nbPositiveVote}</p></span>
                    </g:form>
                </div>
                <div class="child2">
                    <g:form controller="evaluation" action="vote">
                        <g:ifNotAlreadyVoted evaluations="${video.evaluations}" loggedUserId="${sec.loggedInUserInfo(field: 'id')}" >
                            <span class ="unsmash">
                                <g:actionSubmit class="vote" value="false" action="vote" />
                                <g:hiddenField name="vote" value="false" />
                                <g:hiddenField name="type" value="video" />
                                <g:hiddenField name="id" value="${video.id}" />
                            </span>
                        </g:ifNotAlreadyVoted>
                        <g:ifAlreadyVoted evaluations="${video.evaluations}" loggedUserId="${sec.loggedInUserInfo(field: 'id')}" >
                            <g:img dir="images" file="unsmash.png" />
                        </g:ifAlreadyVoted>
                        <span class="nbvote2"><p>${video.nbNegativeVote}</p></span>
                    </g:form>
                </div>
            </sec:ifLoggedIn>
        </div>
    </div>

    <youtube:video videoKey="${video.videoKey}" start="${video.startTime}" end="${video.endTime}" />
</div>
<div>
    <g:if test="${video.comments}">
        <g:include controller="comment" action="index" params="[media: video.id]" ></g:include>
    </g:if>
</div>
<div>
    <g:include controller="comment" action="create" params="[media: video.id]" ></g:include>
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
