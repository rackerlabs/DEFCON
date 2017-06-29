<!DOCTYPE html>
<html>
    <theme:head/>
    <theme:body>
        <theme:layoutTemplate name="header"/>
        
        <div class="container content">
			<div class="row">
				<div class="span12">
	                <div class="page-header">
	                    <theme:layoutTitle/>
	                </div>
		            <div class="row">
						<div class="span2">
							<theme:layoutZone name="user-navigation" />
						</div>
		                <div class="span10">
							<g:if test="${flash.message}">
								<ui:message type="info">${flash.message}</ui:message>
							</g:if>
		                    <theme:layoutZone name="body"/>
		                </div>
		            </div>					
				</div>				
            </div>			
            <theme:layoutTemplate name="footer"/>
		</div>
    </theme:body>
</html>
