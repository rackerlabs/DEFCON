<bean:inputTemplate>
${field}
</bean:inputTemplate>
<bean:selectTemplate>
${field}
</bean:selectTemplate>
<bean:checkBoxTemplate>
${field}
</bean:checkBoxTemplate>
<bean:radioTemplate>
${field}
</bean:radioTemplate>
<bean:textAreaTemplate>
${field}
</bean:textAreaTemplate>
<g:set var="type" value="${type ?: 'text'}"/>
<g:if test="${beanObject}">        
        <g:if test="${type == 'password'}">
            <bean:field type="password" beanName="dummy" bean="${beanObject}" property="${name}" noLabel="${true}"/>
        </g:if>
        <g:else>
            <bean:field type="${type}" beanName="dummy" bean="${beanObject}" property="${name}" noLabel="${true}" required="${required}"/>
        </g:else>
</g:if>
<g:elseif test="${type == 'select'}">
	<g:if test="${attrs?.optionKey}">
		<g:select name="${name}.${attrs.optionKey}" from="${attrs?.from}" value="${value}" keys="${attrs?.keys}" optionKey="${attrs?.optionKey}"/>
	</g:if>
	<g:else>
		<g:select name="${name}" from="${attrs?.from}" value="${value}" keys="${attrs?.keys}" optionKey="${attrs?.optionKey}"/>
	</g:else>
</g:elseif>
<g:elseif test="${type=='uneditable'}">
	<span class="uneditable-input input-large"> ${value}</span>
</g:elseif>
<g:elseif test="${type == 'link'}">
	<span class="uneditable-input input-large"> <g:link controller="${attrs?.linkController}" action="${attrs?.linkAction}" id="${attrs?.linkId}" >${value}</g:link></span>
</g:elseif>
<g:else>
    <input id="${id.encodeAsHTML()}" class="${p.joinClasses(values:[classes, invalid ? 'error' : ''])}" type="${type}" name="${name}" value="${value}" required="${required}"/>
</g:else>
