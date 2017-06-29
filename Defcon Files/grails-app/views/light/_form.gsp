<%@ page import="com.rackspace.it.Light" %>
<%@ page import="com.rackspace.it.Color" %>
<ui:field name="name" type="text" require="true" value="${lightInstance?.name}" />
<ui:field name="ip" value="${lightInstance?.ip}" required="true"/>
<ui:field name="port" type="number" value="${lightInstance?.port}" min="1" max="55901" required="true"/>
<ui:field name="node" type="number" value="${lightInstance?.node}" min="0" max="10" required="true"/>
<ui:field name="net" type="number" value="${lightInstance?.net}" min="0" max="10" required="true"/>
<ui:field name="unit" type="number" value="${lightInstance?.unit}" required="true"/>
<ui:field id="color" name="color" type="select" value="${lightInstance?.color.toString().toUpperCase()}" from="${colors}" keys="${colors*.name()}" required="true" />
<ui:field name="location" type="select" value="${lightInstance?.location?.id}" from="${com.rackspace.it.Location.list()}" optionKey="id" required="true"/>
<div class="row">
	<div class="span1 offset1">
	Active
	</div>
	<div class="span1">    
	<g:checkBox name="active" value="${lightInstance?.active}" checked="${lightInstance?.active}"></g:checkBox>
	</div>
</div>