<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Smash</title>
</head>

<body>

<div class="container" style="width: 100%">
    <div class="jumbotron" style="text-align: center;">
        <h1>SMASH</h1>

        <p>Short Media Sharing</p>
    </div>

    <div class="container" style="width: 70%; margin: 0 auto;">
        <div class="row" style="margin-bottom: 5em; ">
            <div class="col-lg-6" style="text-align: center;">
                <h3>Available Controllers</h3>

            <p>
                <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName }}">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                </g:each>
            </div>

            <sec:ifNotLoggedIn>
                <div class="col-lg-6">
                    <div class="container" style="width: 90%; margin: auto">
                        <h3><g:message code="smash.Register"/></h3>
                        <g:form controller="userManagement" action="signup">
                            <fieldset>

                                <g:if test="${user?.hasErrors()}">
                                    <div class="alert alert-warning">
                                        <g:eachError bean="${user}">
                                            <li><g:message error="${it}"/></li>
                                        </g:eachError>
                                    </div>
                                </g:if>

                                <div class="form-group">
                                    <input type="text" class="form-control" id="email" name="email"
                                           placeholder="Your email"
                                           value="${fieldValue(bean: user, field: 'email')}">
                                </div>

                                <div class="form-group">
                                    <input type="text" class="form-control"
                                           id="username" placeholder="Choose your username"
                                           name="username"
                                           value="${fieldValue(bean: user, field: 'username')}">
                                </div>


                                <div class="form-group">
                                    <input type="password" class="form-control"
                                           id="password" placeholder="Password" name="password">
                                </div>

                                <div class="form-group">
                                    <input type="password" class="form-control"
                                           id="password2" placeholder="Confirm your password"
                                           name="password2">
                                </div>
                                <button type="submit"
                                        class="btn btn-primary pull-left">Sign up &raquo;</button>
                            </fieldset>
                        </g:form>
                    </div>
                </div>
            </sec:ifNotLoggedIn>
        </div>
    </div>

</div>
</body>
</html>
