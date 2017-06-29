
<%@ page import="com.rackspace.it.Location" %>
<!doctype html>
<html>
	<head>
		<theme:layout name="report"/>
		<theme:title text="Location"></theme:title>
	</head>
	<body>
		<theme:zone name="body">
			<ui:table>
				<thead>
					<tr>
						<ui:th text="Name"/>
						<ui:th text="Light / Color"/>
					</tr>					
				</thead>
				<tbody>
					<g:each in="${locationInstanceList}" status="i" var="locationInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>
								<g:link action="show" id="${locationInstance.id}">${fieldValue(bean: locationInstance, field: "name")}</g:link>
							</td>
							<td>
								<g:each in="${locationInstance?.lights?.sort{a,b-> a.name.compareTo(b.name)}}" var="light">
									<p><em>${light}</em> / ${light.color}</p>
								</g:each>
							</td>
						</tr>
					</g:each>
				</tbody>
			</ui:table>
			<theme:zone name="paginate">
				<ui:paginate total="${locationInstanceTotal}" />
			</theme:zone>
		</theme:zone>
	</body>
</html>
