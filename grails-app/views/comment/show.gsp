<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width:inherit">
    <g:if test='${flash.message}'>
        <div class='alert alert-danger alert-dismissable' style="text-align: left">
            ${flash.message}
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        </div>
    </g:if>
    <div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
        <div class="panel-heading text-primary">${commentInstance?.authorUsername}</div>
        <g:form url="[resource: commentInstance]">
            <g:if test="${currentUser.equals(commentInstance.author)}">
                <g:textField class="panel-body" style="width:80%" name="text" id="text" value="${commentInstance?.text}"></g:textField>
                <g:actionSubmit class="btn-primary" type="submit" action="delete" value="Delete"/>
                <g:actionSubmit class="btn-primary" type="submit" action="edit" value="Edit"/>
            </g:if><g:else>
            <g:textField class="panel-body" style="width:80%" name="text" id="text" value="${commentInstance?.text}" disabled="disabled" ></g:textField>
            </g:else>
        </g:form>
    </div>
</div>

</body>
</html>

