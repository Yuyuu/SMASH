<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width: 50%;">
    <div class="panel panel-primary">
        <div class="panel-heading">Add comment to media</div>
        <g:form url="[resource: commentInstance, action: 'save']">
            <div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
               <g:textField class="panel-body" style="width:90%" name="text" id="text" value="${commentInstance?.text}" contenteditable="true"></g:textField>
               <g:hiddenField name="media" id="media" value="${params.media}"></g:hiddenField>
               <g:actionSubmit class="btn-primary" type="submit" action="save" value="Add"/>
            </div>
        </g:form>
    </div>
</div>

</body>
</html>

