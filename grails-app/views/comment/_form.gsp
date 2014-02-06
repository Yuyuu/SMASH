<%@ page import="com.smash.domain.Comment" %>

<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
    <label for="text">
        <g:message default="Add comment"/>
        <span class="required-indicator">*</span>
    </label>
    <richui:richTextEditor name="text" value="${commentInstance?.text}" width="500" height="250" />
</div>

