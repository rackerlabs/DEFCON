<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
			<g:link controller="location" action="list"><g:img dir="images" file="LightTowerSmall.png" class="logo logox" alt="defcon"/></g:link>
			<g:link controller="location" action="list" class="brand">DEFCON</g:link>
			<sec:ifLoggedIn>
				<ul class="nav primary">
					<li class="${controllerName != 'location' ?: 'active' }"><g:link controller="location" action="list">Location</g:link></li>
					<li class="${controllerName != 'light' ?: 'active' }"><g:link controller="light" action="list">Light</g:link></li>				
				</ul>
				<ul class="nav pull-right">
					<li class="divider-vertical"></li>
					<li class="active">
							<g:link controller="logout" action="index">Logout</g:link>
					</li>
				</ul>
			</sec:ifLoggedIn>
        </div>
    </div>
</div>
