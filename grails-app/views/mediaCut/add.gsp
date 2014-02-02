<!DOCTYPE html>
<%@page import="com.smash.domain.MediaType"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="smash"/>
    <title>Adding Media</title>
</head>

<body>



<div class="container" style="width: 40%">
    <div class="panel panel-primary">
        <div class="panel-heading">Add a media</div>

        <div class="panel-body">
            <g:if test="${flash.message}">
                <div class="alert alert-info" role="status">${flash.message}</div>
            </g:if>
            <div class="row">

                <div class="" style="width: 80%; margin: auto">
                    <g:form controller="MediaCut" action="create" method="POST">
                        <fieldset>

                            <div class="form-group">
                                <input type="text" class="form-control" id="title" name="title"
                                       placeholder="Title of your media"
                                       autofocus="true">
                            </div>

                            <div class="form-group">
                                <input type="text" class="form-control"
                                       id="description" placeholder="Short description"
                                       name="description">
                            </div>


                            <div class="form-group">
                                <input type="text" class="form-control"
                                       id="tags" placeholder="Tags separated by spaces" name="tags">
                            </div>

                            <div class="form-group">
                                <input type="text" class="form-control"
                                       id="link" placeholder="Link of your media"
                                       name="link">
                            </div>                                                    
                            
                            <div class="form-group">
                            Select your media type:
                                 <select name="type" class="selectpicker" title='Choose one of the following...'>
									<option value="${MediaType.FILM.value }">Film extract</option>
	                            	<option value="${MediaType.MOVIE.value }">Movie extract</option>
	                            	<option value="${MediaType.MUSIC.value }">Music extract</option>
	                            	<option value="${MediaType.RADIO.value }">Radio extract</option>
	                            	<option value="${MediaType.TV.value }">TV extract</option>
								</select>
                            </div>


                            <button type="submit"
                                    class="btn btn-primary">Create &raquo;</button>

                        </fieldset>
                    </g:form>

                </div>

            </div>

        </div>

    </div>
</div>

</body>
</html>
