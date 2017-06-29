<%@ page import="com.rackspace.it.Location" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="dataentry"/>
		<theme:title text="Create New Location"></theme:title>
	</head>
	<body>
		<theme:zone name="body">
			<g:hasErrors bean="${locationInstance}">
				<ui:message type="error">
				<ul class="errors" role="alert">
					<g:eachError bean="${locationInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</ui:message>
			</g:hasErrors>

			<ui:form action="save" controller="location" method="post">
		<div class="row">
			<div class="span9">
				<ui:field name="name" type="text" require="true" value="${locationInstance?.name}"/>
			</div>
		</div>
      			<g:render template="form"/>
				<ui:actions>
					<div class="form-actions">
						<ui:button kind="submit" mode="primary">Save</ui:button>
					</div>
				</ui:actions>
			</ui:form>
		</theme:zone>
	</body>
</html>
