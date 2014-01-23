<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Smash</title>
</head>

<body>

<div id="container" class="container-fluid">
    <div id="navbar" class="navbar">
        <div class="navbar-inner">
            <ul class="nav">
                <li><a href="#">SMASH</a></li>
            </ul>

            <sec:ifNotLoggedIn>
                <ul class="nav pull-right">
                    <li><g:link url="#"><g:message code="smash.Sign-in"/></g:link></li>
                    <li><g:link url="#"><g:message code="smash.Register"/></g:link></li>
                </ul>
            </sec:ifNotLoggedIn>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="hero-unit" style="text-align: center">
        <h1>SMASH</h1>

        <p>Short Media Sharing</p>
    </div>

    <div class="container-fluid" style="width: 75%; margin: 0 auto;">
        <div class="row-fluid" style="text-align: center; margin-bottom: 5em; ">
            <div style="padding: 1em;">
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
