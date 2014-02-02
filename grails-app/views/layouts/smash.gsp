<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle/></title>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'twitter-bootstrap/bootstrap.min.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'twitter-bootstrap/bootstrap-select.min.css')}" />
    <script language="JavaScript" src="${resource(dir: 'js', file: 'jQuery/jquery-2.0.3.min.js')}"></script>
    <script language="JavaScript" src="${resource(dir: 'js', file: 'twitter-bootstrap/bootstrap.min.js')}"></script>
    <script language="JavaScript" src="${resource(dir: 'js', file: 'twitter-bootstrap/bootstrap-select.min.js')}"></script>
	<script type="text/javascript">
        $(window).on('load', function () {

            $('.selectpicker').selectpicker({
                'selectedText': 'cat'
            });

            // $('.selectpicker').selectpicker('hide');
        });
    </script>


    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'smash/smash.css')}" />
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

					<li>
                        <a href="${createLink(controller: 'mediaCut')}">
                            <span class="glyphicon glyphicon-plus-sign"></span>
                            Add a new media
                        </a>
                    </li>

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

<g:layoutBody/>
</body>
</html>