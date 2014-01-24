<html>
<head>
    <meta name='layout' content='smash'/>
    <title><g:message code="smash.Sign-in"/></title>
</head>

<body>
<div class="container login-box">
    <h3><g:message code="smash.Sign-in.title"/></h3>
    <form class="form-horizontal well" role="form" action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
        <div class="form-group" style="width: 90%; margin-left: auto; margin-right: auto">
            <label for="inputUsername" class="control-label" style="margin-bottom: 5px;"><g:message code="smash.Login"/></label>
            <input type="text"
                   class="form-control"
                   id="inputUsername"
                   placeholder="${g.message(code: 'smash.Login')}"
                   autofocus="true"
                   name="j_username">
        </div>
        <div class="form-group" style="width: 90%; margin-left: auto; margin-right: auto">
            <label for="inputPassword" class="control-label" style="margin-bottom: 5px;"><g:message code="smash.Password"/></label>
            <input type="password"
                   class="form-control"
                   id="inputPassword"
                   placeholder="${g.message(code: 'smash.Password')}"
                   name="j_password">
        </div>
        <div class="form-group" style="width: 90%; margin-left: auto; margin-right: auto">
            <div class="">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"
                               name='${rememberMeParameter}'
                               <g:if test='${hasCookie}'>checked='checked'</g:if>><g:message code="smash.Remember-me"/>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group" style="width: 90%; margin-left: auto; margin-right: auto">
            <div class="">
                <button type="submit" class="btn btn-primary"><g:message code="smash.Sign-in"/></button>
            </div>
        </div>
    </form>

    <g:if test='${flash.message}'>
        <div class='login-error-message alert alert-info'>${flash.message}</div>
    </g:if>
    <g:if test='${flash.error}'>
        <div class='login-error-message alert alert-error'>${flash.error}</div>
    </g:if>
</div>

</body>
</html>