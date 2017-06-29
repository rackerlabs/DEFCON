<%@ page import="com.rackspace.it.Light" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="dataentry"/>
		<theme:title text="Lights"></theme:title>			
	</head>
	<body>
		<theme:zone name="body">
			<ui:form action="edit" id="${lightInstance?.id}" method="GET">
				<ui:field name="name" type="uneditable" value="${lightInstance?.name}"/>
				<ui:field name="ip" value="${lightInstance?.ip}" type="uneditable"/>
				<ui:field name="port" value="${lightInstance?.port}" type="uneditable"/>
				<ui:field name="node" value="${lightInstance?.node}"  type="uneditable"/>
				<ui:field name="net"  value="${lightInstance?.net}" type="uneditable"/>
				<ui:field name="unit" value="${lightInstance?.unit}" type="uneditable"/>
				<ui:field name="active" value="${lightInstance?.active}" type="uneditable"/>
				<ui:field name="color" value="${lightInstance?.color}" type="uneditable"/>

				<ui:field name="location" value="${lightInstance?.location}" type="link" linkController="location" linkAction="show" linkId="${lightInstance?.location?.id}"/>
				<ui:actions>
					<div class="form-actions">
						<ui:button kind="button" mode="primary" id="${lightInstance.id}" action="edit">Edit</ui:button>
					</div>
				</ui:actions>				
			</ui:form>
		</theme:zone>
	</body>
</html>
