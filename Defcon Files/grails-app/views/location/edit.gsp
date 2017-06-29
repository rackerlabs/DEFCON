<%@ page import="com.rackspace.it.Location" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="dataentry"/>
		<theme:title text="Edit Location"></theme:title>
	</head>
	<body>

		<theme:zone name="body">

			<g:hasErrors bean="${locationInstance}">
				<ui:message type="error">
					<ul role="alert">
						<g:eachError bean="${locationInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</ui:message>
			</g:hasErrors>

			<ui:form action="update" controller="location" method="post" id="${locationInstance.id}">
				<g:hiddenField name="id" value="${locationInstance?.id}" />
				<g:hiddenField name="version" value="${locationInstance?.version}" />
							<ui:field name="name" type="text" require="true" value="${locationInstance?.name}"/>
							<g:if test="${locationInstance?.lights?.size() != 0}">
								<ui:field name="color" type="text" required="" value="${locationInstance?.lights?.color[0]}" hint="Location has to have a light for the update to this field to save. ">
								</ui:field>
							</g:if>

							<g:if test="${locationInstance?.lights?.size() == 0}">
								<ui:field name="color" type="uneditable" required="" value="${locationInstance?.lights?.color[0]}" hint="Location has to have a light for you to be able to edit this field. " >
								</ui:field>
							</g:if>
				<div class="row-fluid">
      				<div class="span10 offset2">
      					<g:render template="form"/>
    				</div>
  				</div>
				<ui:actions>
					<div class="form-actions">
						<g:if test="${locationInstance?.lights?.size() != 0}">
						<ui:button kind="submit" mode="primary" action="changeLights"  id="${locationInstance.id}">Update Light Color</ui:button>
						</g:if>

						<g:if test="${locationInstance?.lights?.size() == 0}">
						<ui:button kind="submit" mode="primary" action="changeLights" disabled="true" id="${locationInstance.id}">Update Light Color</ui:button>
						</g:if>

						<ui:button kind="submit" mode="danger" id="${locationInstance.id}">Delete</ui:button>
					</div>
				</ui:actions>
			</ui:form>

		</theme:zone>

	</body>
</html>