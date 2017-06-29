<%@ page import="com.rackspace.it.Light" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="dataentry"/>
		<theme:title text="Lights"></theme:title>
	</head>
	<body>
		<theme:zone name="body">
				<g:hasErrors bean="${lightInstance}">
					<ui:message type="error">
						<ul role="alert">
							<g:eachError bean="${lightInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
							</g:eachError>
						</ul>
					</ui:message>
				</g:hasErrors>
				<ui:form action="save" controller="light" method="post">
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