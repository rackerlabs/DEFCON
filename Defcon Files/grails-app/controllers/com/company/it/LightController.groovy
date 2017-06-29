package com.rackspace.it

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LightController {

    def lightService
	def locationService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def lights = lightService.list(params)
        def count = lightService.count()

        [lightInstanceList: lights, lightInstanceTotal: count]
    }

    def create() {
        [lightInstance: new Light(params), colors: Color.values()]
    }

    def save() {      
        def light = lightService.save(params, false)

        if(light.hasErrors()) {
			render(view: "create", model: [lightInstance: light, colors: Color.values(), locations: locationService.list()])
        } else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'light.label', default: 'Light'), light.id])
            redirect(action: "show", id: light.id)
        }
    }

    def show(Long id) {
        def lightInstance = lightService.get(id)
        if (!lightInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'light.label', default: 'Light'), id])
            redirect(action: "list")
            return
        }

        [lightInstance: lightInstance]
    }

    def edit(Long id) {
        def light = lightService.get(id)
        if (!light) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'light.label', default: 'Light'), id])
            redirect(action: "list")
            return
        }
      
        [lightInstance: light, colors: Color.values()]
    }

    def update(Long id) {
        def light = lightService.get(id)

        if (!light) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'light.label', default: 'Light'), id])
            redirect(action: "list")
            return 
        }

        light = lightService.save(params, false)

        if (light.hasErrors()) {
            render(view: "edit", model: [lightInstance: light,colors: Color.values()])
        } else {
            flash.message = message(code: 'default.updated.message', args: [message(code: 'light.label', default: 'Light'), light.id])
            redirect(action: "show", id: light.id)
        }
    }

    def changeLight(Long id) {
        def light
        try {
            light = lightService.get(id)
            lightService.changeLight(light)
            flash.message = message(code: 'default.updated.message', args: [message(code: 'light.label', default: 'Light'), light.id])
        } catch(Exception e) {
            flash.message = "Light has not been changed: '${e.message}'"
        } 
       
         render(view: "show", model: [lightInstance: light])
    }

    def delete(Long id) {
        try {
            lightService.delete(id)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'light.label', default: 'Light'), id])
            redirect(action: "list")   
        } catch (Exception e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'light.label', default: 'Light'), id])
            redirect(action: "show", id: id)
        }
    }
}
