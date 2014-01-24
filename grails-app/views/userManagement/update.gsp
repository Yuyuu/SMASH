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
            <g:link uri="/" class="navbar-brand">Smash</g:link>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <p class="navbar-text"><sec:username/></p>

                <li>
                    <a href="${createLink(controller: 'logout')}"><g:message code="smash.Sign-out"/></a>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container" style="width: 40%">
<div class="panel panel-primary">
    <div class="panel-heading">Account information</div>
    <div class="panel-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
        </g:if>
        <g:if test="${user?.hasErrors()}">
            <div class="alert alert-warning">
                <g:eachError bean="${user}">
                    <li><g:message error="${it}"/></li>
                </g:eachError>
            </div>
        </g:if>
        <div class="row">

            <div class="" style="width: 80%; margin: auto">
                <g:form controller="userManagement" action="update" method="POST">
                    <fieldset>

                        <div class="form-group">
                            <input type="text" class="form-control" id="email" name="email"
                                   placeholder="Update your email"
                                   value="${fieldValue(bean: user, field: 'email')}"
                                   autofocus="true">
                        </div>

                        <div class="form-group">
                            <input type="text" class="form-control"
                                   id="username" placeholder="Update your username"
                                   name="username"
                                   value="${fieldValue(bean: user, field: 'username')}">
                        </div>


                        <div class="form-group">
                            <input type="password" class="form-control"
                                   id="password" placeholder="New password" name="password">
                        </div>

                        <div class="form-group">
                            <input type="password" class="form-control"
                                   id="password2" placeholder="Confirm your password"
                                   name="password2">
                        </div>


                        <button type="submit"
                                class="btn btn-primary">Save &raquo;</button>

                        <g:link uri="/" class="btn btn-default" role="button">Cancel</g:link><br/>

                    </fieldset>
                </g:form>

            </div>

        </div>

    </div>

</div>
</div>
</body>
</html>
