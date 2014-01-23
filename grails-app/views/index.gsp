<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Smash</title>
</head>

<body>

<div id="container" class="container-fluid" style="width: 96%; margin: 0 auto;">
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Smash</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <sec:ifNotLoggedIn>
                    <li><g:link controller="login"><g:message code="smash.Sign-in"/></g:link></li>
                </sec:ifNotLoggedIn>
            </ul>
        </div>
    </nav>
</div>

<div class="container">
    <div class="jumbotron" style="text-align: center;">
        <h1>SMASH</h1>

        <p>Short Media Sharing</p>
    </div>

    <div class="container-fluid" style="width: 80%; margin: 0 auto;">
        <div class="row" style="text-align: center; margin-bottom: 5em; ">
            <div>
                <h3>Available Controllers</h3>

            <p>
                <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                </g:each>
            </div>
        </div>
    </div>

</div>
</body>
</html>
