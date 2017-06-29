<%=packageName ? "package ${packageName}\n\n" : ''%>

class ${className}Controller {
	
	def ${propertyName.minus("Instance")}Service
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [${propertyName}List: ${propertyName.minus("Instance")}Service.list(params), ${propertyName}Total: ${propertyName.minus("Instance")}Service.count()]
    }

    def create() {
        [${propertyName}: new ${className}(params)]
    }

    def save() {
        def ${propertyName} = ${propertyName.minus("Instance")}Service.save(params, false)
		
        if (${propertyName}.hasErrors()) {
            render(view: "create", model: [${propertyName}: ${propertyName}])
		} else {
	        flash.message = message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
	        redirect(action: "show", id: ${propertyName}.id)				
		}
    }

    def show(Long id) {
        def ${propertyName} = ${propertyName.minus("Instance")}Service.get(id)
		
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }

        [${propertyName}: ${propertyName}]
    }

    def edit(Long id) {
        def ${propertyName} = ${propertyName.minus("Instance")}Service.get(id)
		
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }

        [${propertyName}: ${propertyName}]
    }

    def update(Long id) {
        def ${propertyName} = ${propertyName.minus("Instance")}Service.get(id)
		
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }
		
		${propertyName} = ${propertyName.minus("Instance")}Service.save(params, false)

        if (${propertyName}.hasErrors() ) {
            render(view: "edit", model: [${propertyName}: ${propertyName}])
		} else {
	        flash.message = message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
	        redirect(action: "show", id: ${propertyName}.id)			
		}
    }

    def delete(Long id) {
		try {
			${propertyName.minus("Instance")}Service.delete(id)
            flash.message = message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])			
            redirect(action: "list")
		} catch ( e ) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "show", id: id)				
		}
    }
}
