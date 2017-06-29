<%@ page import="com.rackspace.it.Location" %>

<div class="row">
  	<div class="span2 offset1">
  		<label for="lights">
			<g:message code="location.lights.label" default="Lights" />
		</label>
	</div>
  	<div class="span5">
  		<ui:button kind="anchor" mode="primary" action="create" controller="light" params="['location.id': locationInstance?.id]" >Add Light</ui:button>
  	</div>
</div>

<div class="row">
	<div class="span4 offset1">
		<ul>
			<g:each in="${locationInstance?.lights?.sort{a,b-> a.name.compareTo(b.name)} }" var="l">
				<li>
    				<g:link controller="light" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link>
    			</li>
			</g:each>
		<ul>
	</div>
</div>
