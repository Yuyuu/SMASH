<%@ page import="com.smash.media.MediaCut; com.smash.media.Video" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
</head>

<body>
<div style="margin-top: 2%">
    <div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
        <div class="panel-heading text-primary">${commentInstance?.authorUsername}</div>
        <g:form url="[resource: commentInstance]">
            <g:if test="${currentUser.equals(commentInstance.author)}">
                <g:textField class="panel-body" required="required" pattern=".{1,}" style="width:80%" name="text" id="text" value="${commentInstance?.text}"></g:textField>
                <div class="btn-group pull-right">
                    <g:actionSubmit class="btn btn-danger" type="submit" action="delete" value="Delete"/>
                    <g:actionSubmit class="btn btn-primary" type="submit" action="edit" value="Edit"/>
                </div>
            </g:if><g:else>
                <g:textField class="panel-body"  style="width:80%" name="text" id="text" value="${commentInstance?.text}" disabled="disabled" ></g:textField>
            </g:else>
        </g:form>
    </div>
</div>

</body>
</html>

