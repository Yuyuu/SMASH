<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Account update</title>
</head>

<body>

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
                                       id="password2" placeholder="Confirm your new password"
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

<div class="container" style="width: 40%">
    <hr>
    <button class="btn btn-danger" data-toggle="modal" data-target="#deleteAccountModal">
        <span class="glyphicon glyphicon-remove"></span> Delete your account
    </button>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteAccountModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Account deletion</h4>
            </div>

            <div class="modal-body">
                Once your account is deleted, all of your data will be lost. This operation cannot be undone.<br>
                Are you sure you want to delete your account?
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <g:link controller="userManagement" action="delete" type="button" class="btn btn-danger">Delete</g:link>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    $("#password").popover({
        trigger: 'focus',
        placement: 'left',
        content: 'Only fill up this field if you want to change your password'
    });
</script>

</body>
</html>
