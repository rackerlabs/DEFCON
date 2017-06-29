<html>
	<head>
		<theme:layout name="home"/>
		<theme:title text="Defcon"></theme:title>
	</head>
	<body>
		<theme:zone name="body">

			<ui:form url="${postUrl}" method="POST" id="loginForm" autocomplete="off">
				<ui:field name="j_username" id="username" label="Username"/>
				<ui:field name="j_password" id="password" type="password" label="Password"/>
				<ui:actions>
					<div class="form-actions">
						<ui:button mode="primary">Log in</ui:button>
					</div>
				</ui:actions>
			</ui:form>
		</theme:zone>	
	</body>
</html>
