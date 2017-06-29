package com.rackspace.it

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ApiController {
    static allowedMethods = [light:'POST',
                             lights:'POST',
                             location:'POST',
                             locations:'POST',
                             all:'POST',
                             status: 'GET']
    def lightService
    
    def light() {
        try {
			lightService.changeLight( ["id": params.id, "color": params.color])
            render ( text: "Light Updated", status: response.SC_OK )
        } catch ( e ) {
            render ( text: "Error Updating Light (" + e.getMessage() + ")", status: response.SC_CONFLICT )
        }
    }

    def lights() {
        try {
            lightService.changeLights(request.JSON.lights)
            render ( text: "Lights Updated", status: response.SC_OK )
        } catch ( e ) {
            render ( text: "Error Updating Lights(" + e.getMessage() + ")", status: response.SC_CONFLICT )
        }
    }

    def location() {
        try {
            lightService.changeLocation( [name: params.name, color: params.color] )
            render ( text: "Location Updated", status: response.SC_OK )
        } catch ( e ) {
            render ( text: "Error Updating Location", status: response.SC_CONFLICT )
        }
    }

    def locations() {
        try {
            lightService.changeLocations(request.JSON.locations)
            render ( text: "Locations Updated", status: response.SC_OK )
        }
        catch(Exception e) {
            render ( text: "Error Updating Locations(" + e.getMessage() + ")", status: response.SC_CONFLICT )
        }        
    }

    def all() {
        try {
            lightService.changeAll( params.color )
            render ( text: "All Lights Updated", status: response.SC_OK )
        } catch( e ) {
            render ( text: "Error Updating All Lights(" + e.getMessage() + ")", status: response.SC_CONFLICT )
        }
    }

    def status() {
        try {
            render( lightService.statusAll() as JSON)
        } catch( e ) {
            render ( text: "Error getting light status", status: response.SC_CONFLICT )
        }
    }
}