<%@ page import="com.rackspace.it.Location" %>
<%@ page import="com.rackspace.it.Light" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="dataentry"/>
		<theme:title text="Location"></theme:title>
	</head>
	<body>
		<theme:zone name="body">
		<ui:form action="update" controller="location" id="${locationInstance?.id}" method="post">
			<div class="row">
				<div class="span9">
					<ui:field name="name" type="text" value="${locationInstance?.name}"/>
				</div>
			</div>
			<g:render template="form"/>
				<ui:actions>
					<div class="form-actions">
						<ui:button kind="submit" mode="primary" id="${locationInstance?.id}" params="['location.id': locationInstance?.id, 'location.name': locationInstance?.name]"action="update">Update</ui:button>
						<ui:button kind="submit" mode="secondary" id="${locationInstance?.id}" action="edit">Edit</ui:button>
						<ui:button kind="submit" mode="danger" id="${locationInstance.id}">Delete</ui:button>
					</div>
				</ui:actions>
		</ui:form>
		</theme:zone>
	</body>
</html>