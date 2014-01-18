<%@ page import="com.smash.domain.Comment" %>



<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'text', 'error')} required">
    <label for="text">
        <g:message code="comment.text.label" default="Text"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="text" required="" value="${commentInstance?.text}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'author', 'error')} required">
    <label for="author">
        <g:message code="comment.author.label" default="Author"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="author" name="author.id" from="${com.smash.User.list()}" optionKey="id" required=""
              value="${commentInstance?.author?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'media', 'error')} required">
    <label for="media">
        <g:message code="comment.media.label" default="Media"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="media" name="media.id" from="${com.smash.domain.MediaCut.list()}" optionKey="id" required=""
              value="${commentInstance?.media?.id}" class="many-to-one"/>
</div>

