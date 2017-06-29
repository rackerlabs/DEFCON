<%@ page import="com.rackspace.it.Light" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="report"/>
		<theme:title text="Lights"></theme:title>	
	</head>
	<body>	
		<theme:zone name="body">	
			<ui:table>
				<thead>
					<tr>
						<ui:th text="Name" />
						<ui:th text="IP"/>
						<ui:th text="Color"/>
						<ui:th text="Port"/>
						<ui:th text="Node"/>
						<ui:th text="Net"/>
					</tr>
				</thead>
				<tbody>
					<g:each in="${lightInstanceList}" status="i" var="light">
						<ui:tr>
							<td><g:link action="show" id="${light.id}">${light.name}</g:link></td>
							<td>${light.ip}</td>
							<td>${light.color}</td>
							<td>${light.port}</td>
							<td>${light.node}</td>
							<td>${light.net}</td>
						</ui:tr>
					</g:each>
				</tbody>
			</ui:table>
		</theme:zone>
		<theme:zone name="paginate">
			<ui:paginate total="${lightInstanceTotal}" />

		</theme:zone>
	</body>
</html>