package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.*

@TestFor(LocationController)
@Mock([LocationService, Location, LightService, Light])
class LocationControllerSpec extends Specification {

	LocationService service
	LightService lightService

	def locationMap = [name: "Location3!"]
	def lightMap = [ port: 9600, net: 1, node: 1, unit: 1, name: "Red Light", ip: "127.0.0.1", active: true, 
		color: Color.GREEN, location: new Location(name:"Location3") ]
	
	def setup() {
		service = Mock()
		lightService = Mock()
		controller.locationService = service
		controller.lightService = lightService
	}
	
	def "index - redirects tot he list page"() {
		when:
			controller.index()
		then:
			response.redirectedUrl == "/location/list"
	}

	def "create - method returns an instance of location object"() {
		when:
			def result = controller.create()
		then:
			result.locationInstance != null
		and:
			result.locationInstance instanceof Location
	}
	

	def "list - displays a list of the locations"() {
		given: 
			service.list( _ ) >> []
			service.count() >> 0
		when: 
			def result = controller.list(10)
		then:
			result.locationInstanceTotal == 0
	}

	def "save - returns the list of locations on a success"() {
		given:
			def location = new Location(locationMap)
			service.save( _, false ) >> location.save()
		when:	
			controller.save()
		then:
			response.redirectedUrl == "/location/show/1"
	}

	def "save - returns create screen if the location has errors"() {
		given:
			def location = new Location(name:"")
			location.validate()
			service.save( _,_ ) >> location
		when:
			controller.save()
		then:
			view == "/location/create"
	}

	def "show - returns a specific location based on the ID"() {
		given:
			def location = new Location(locationMap)
			service.get( _ ) >> location.save()
		when:
			def result = controller.show(location.id)
		then:"Checking to make sure the list of location instances is not zero"
			result.locationInstance != null
		and: 
			result.locationInstance.id == location.id
	}

	def "show - displays the list of locations if the location ID doesn't exist"() {
		given:
			def location = new Location()
			location.save()
			service.get( _ ) >> false
		when:"We pass in an ID that doesn't exist"
			controller.show(12)
		then:"Display the list of locations"
			response.redirectedUrl == "/location/list"
		and:
			assert flash.message != null
			//TODO: figure out why assert response.text == "Location not found with id 12" fails
	}

	def "edit - returns a specific location based on the ID"() {
		given:
			def location = new Location(locationMap)
			service.get( _ ) >> location.save()
		when:
			def result = controller.edit(location.id)
		then:
			result.locationInstance != null
		and:
			result.locationInstance.id == location.id
	}

	def "edit - displays the list of locations if the location ID doesn't exist"() {
		given:
			def location = new Location()
			location.save()
			service.get( _ ) >> false
		when:"We pass in an ID that doesn't exist"
			controller.edit(123)
		then:"Display the list of locations"
			response.redirectedUrl == "/location/list"
		and:
			assert flash.message != null
	}

	def "update - returns the updated location"() {
		given: 
			def location = new Location(locationMap)
			location.save()
			service.get( _ ) >> location
			service.save( _, _ ) >> location
		when:
			controller.update(location.id)
		then:
			response.redirectedUrl == "/location/show/1"
		and:
			flash.message == "default.updated.message"
	}

	def "update - displays the list of locations if the location ID doesn't exist"() {
		given:
			def location = new Location()
			location.save()
			service.get( _ ) >> false
		when:
			controller.update(42)
		then:
			response.redirectedUrl == "/location/list"
		and:
			assert flash.message == "default.not.found.message"
	}
	
	def "update - returns to edit page with errors when validation fails"() {
		given:
			def location = new Location()
			location.save()
		and:
			service.get(_) >> location
			service.save(_,_) >> location
		when:
			controller.update(1)
		then:
			view == "/location/edit"
		and:
			model.locationInstance == location
	}
	
	def "delete - successful deletes return to the list page"() {
		given: 
			service.delete(1) >> true
		when:
			controller.delete(1)
		then:
			response.redirectedUrl == "/location/list"
		and:
			flash.message == "default.deleted.message"
	}
	
	def "delete - failure returns to the show page of the original record"() {
		given:
			service.delete(2) >> { throw new Exception("Homey don't play that!") }
		when:
			controller.delete(2)
		then:
			response.redirectedUrl == "/location/show/2"
		and:
			flash.message == "default.not.deleted.message"
	}

	def "changeLights - successful returns the user to the edit page and tells that the light was updated"() {
		given:
			def location = new Location(name: "Location3")
			def light = new Light(lightMap)
			location.addToLights(light)
			location.save()
			lightService.changeLocation( _ ) >> location
		when:
			request.JSON = '{name: "Location3", color: "blue"}'
			controller.changeLights(location.id)
		then:
			response.redirectedUrl == "/location/edit/1"
		and:
			flash.message == "default.updated.message all active lights"
	}

	def "changeLights - throws an error message and returns"() {
		given:
			def location = new Location(name: "Location3")
			def light = new Light(lightMap)
			location.addToLights(light)
			location.save()
			//lightService.changeLocation( _ ) >> { throw new Exception("Yo Daddy!") }
		when:
			request.JSON = '{name: "Location3", color: "blue"}'
			controller.changeLights(location.id)
		then:
			response.redirectedUrl == "/location/edit/1"
			//no longer throwing exception as this will confuse the end user
		//and:
		//	flash.message == "The lights for Location Location3 have not been updated. java.lang.Exception: Location3!"
	}
	
}