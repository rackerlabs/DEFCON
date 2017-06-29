package com.rackspace.it

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LocationController {
	
	def locationService
	def lightService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(params.max ?: 10, 100)
        [locationInstanceList: locationService.list(params), locationInstanceTotal: locationService.count()]
    }

    def create() {
        [locationInstance: new Location(params)]
    }

    def save() {
        def locationInstance = locationService.save(params, false)
		
        if (locationInstance.hasErrors()) {
            render(view: "create", model: [locationInstance: locationInstance])
		} else {
	        flash.message = message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])
	        redirect(action: "show", id: locationInstance.id)				
		}
    }

    def show(Long id) {
        def locationInstance = locationService.get(id)
		
        if (!locationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), id])
            redirect(action: "list")
            return
        }

        [locationInstance: locationInstance]
    }

    def edit(Long id) {
        def locationInstance = locationService.get(id)
		
        if (!locationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), id])
            redirect(action: "list")
            return
        }

        [locationInstance: locationInstance]
    }

    def update(Long id) {
        def locationInstance = locationService.get(id)
		
        if (!locationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), id])
            redirect(action: "list")
            return
        }
		
		locationInstance = locationService.save(params, false)

        if (locationInstance.hasErrors() ) {
            render(view: "edit", model: [locationInstance: locationInstance])
		} else {
	        flash.message = message(code: 'default.updated.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])
	        redirect(action: "show", id: locationInstance.id)			
		}
    }

    def delete(Long id) {
		try {
			locationService.delete(id)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'location.label', default: 'Location'), id])			
            redirect(action: "list")
		} catch ( e ) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'location.label', default: 'Location'), id])
            redirect(action: "show", id: id)				
		}
    }

    def changeLights(Long id) {
        try{
            lightService.changeLocation(params)
            flash.message = message(code: 'default.updated.message',args: [message(code: 'location.label', default: 'Location'), id] ) + " all active lights"
            redirect(action: "edit", id: id)    
        } catch( Exception e ){
            //println e.cause.message

            flash.message = "The lights for Location " + params.name +" have not been updated. " + e.cause.message
            redirect(action: "edit", id: id)  
        }
    }
}
