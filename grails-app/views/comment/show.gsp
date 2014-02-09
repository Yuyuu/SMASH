<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div class="container" style="width:inherit">
    <div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
        <div class="panel-heading text-primary">${commentInstance?.authorUsername}</div>
        <g:form url="[resource: commentInstance]">
            <g:textField class="panel-body" style="width:80%" name="text" id="text" value="${commentInstance?.text}" contenteditable="false"></g:textField>
            <g:if test="${currentUser.equals(commentInstance.author)}">
                <g:actionSubmit class="btn-primary" type="submit" action="delete" value="Delete"/>
            </g:if>
        </g:form>
    </div>
</div>

</body>
</html>

