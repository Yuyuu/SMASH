<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width: 60%;margin-top: 2%; margin-bottom: 2%">
    <g:if test='${flash.message}'>
        <div class='alert alert-danger alert-dismissable' style="text-align: left">
            ${flash.message}
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        </div>
    </g:if>
    <g:form url="[resource: commentInstance, action: 'save']">
    <div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
       <g:textField class="panel-body" required="required" pattern=".{1,}" style="width:80%" name="text" id="text" value="${commentInstance?.text}" contenteditable="true"></g:textField>
       <g:hiddenField name="media" id="media" value="${params.media}"></g:hiddenField>
       <div class="btn-group pull-right col-md-2">
           <g:actionSubmit class="btn btn-primary" type="submit" action="save" value="Add comment"/>
        </div>
    </div>
    </g:form>
</div>

</body>
</html>

