//location1 is the name of one of the locations.
package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.*

@TestFor(LightController)
@Mock([LightService, LocationService, Light])
class LightControllerSpec extends Specification {

	LightService service
	LocationService locationService

	def lightMap = [port: 1, net: 1, node: 1, unit: 1, name: "Red Light", ip: "1.1.1.1", active: true, color: Color.GREEN]
	
	def setup() {
		service = Mock()
		locationService = Mock()
		controller.lightService = service
		controller.locationService = locationService
	}

	def "list - displays a list of the lights"() {
		given: 
			service.list( _ ) >> []
			service.count() >> 0
		when: 
			def result = controller.list(10)
		then:
			result.lightInstanceTotal == 0
	}

	def "save - returns the list of lights on a success"() {
		given:
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			light.setColor("blue")
			service.save( _, false ) >> light.save()
		when:	
			controller.save()
		then:
			response.redirectedUrl == "/light/show/1"
	}

	def "save - returns create screen if the light has errors"() {
		given:
			def light = new Light()
			light.save()
			service.save( _,_ ) >> light
		and:
			locationService.list() >> []
		when:
			controller.save()
		then:
			view == "/light/create"
	}

	def "show - returns a specific light based on the ID"() {
		given:
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			service.get( _ ) >> light.save()
		when:
			def result = controller.show(light.id)
		then:"Checking to make sure the list of light instances is not zero"
			result.lightInstance != 0
	}

	def "show - displays the list of lights if the light ID doesn't exist"() {
		given:
			def light = new Light()
			light.save()
			service.get( _ ) >> false
		when:"We pass in an ID that doesn't exist"
			controller.show(12)
		then:"Display the list of lights"
			response.redirectedUrl == "/light/list"
		and:
			assert flash.message != null
			//TODO: figure out why assert response.text == "Light not found with id 12" fails
	}

	def "edit - returns a specific light based on the ID"() {
		given:
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			service.get( _ ) >> light.save()
		when:
			def result = controller.edit(light.id)
		then:
			result.lightInstance != 0
	}

	def "edit - displays the list of lights if the light ID doesn't exist"() {
		given:
			def light = new Light()
			light.save()
			service.get( _ ) >> false
		when:"We pass in an ID that doesn't exist"
			controller.edit(123)
		then:"Display the list of lights"
			response.redirectedUrl == "/light/list"
		and:
			assert flash.message != null
	}


	def "update - returns the updated light"() {
		given: 
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			light.save()
			service.get( _ ) >> light
			service.save( _ , false ) >> light
		when:
			controller.update(light.id)
		then:
			response.redirectedUrl == "/light/show/1"
		and:
			assert flash.message == "default.updated.message"
	}

	def "update - displays the list of lights if the light ID doesn't exist"() {
		given:
			def light = new Light()
			light.save()
			service.get( _ ) >> false
		when:
			controller.update(42)
		then:
			response.redirectedUrl == "/light/list"
		and:
			assert flash.message == "default.not.found.message"
	}

	def "update - displays the edit page if the light has errors"() {
		given:
			def light = new Light()
			light.save()
			service.get( _ ) >> light
			service.save( _ , false ) >> light
		when:
			controller.update(21)
		then:
			view == "/light/edit"
	}

	def "delete - redirects to the list of lights after successfully deleting a light"() {
		given:
			def light = new Light(lightMap)
			light.save()
			service.delete( _ ) >> light
		when:	
			controller.delete(1)
		then:
			flash.message == "default.deleted.message"
		and:	
			response.redirectedUrl == "/light/list"
	}

	def "delete - redirects to the show if there is an error trying to delete the light"() {
		given:
			def light = new Light()
			light.save()
			service.delete( _ ) >> {throw new Exception()}
		when:
			controller.delete(312)
		then:
			flash.message == "default.not.deleted.message"
		and:
			response.redirectedUrl == "/light/show/312"
	}

	def "index - redirects to a list of lights"() {
		given:
			def light = new Light(lightMap)
			light.save()
		when:
			controller.index()
		then:
			response.redirectedUrl == "/light/list"
	}

	def "create - creates a new light instance"() {
		given:
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			new Light( _ ) >> light.save()
		when:
			def result = controller.create()
		then:
			result.size() != 0
	}

	def "changeLight - updates the light"() {
		given:
			def light = new Light(lightMap)
			light.location = new Location(name:"Location1")
			new Light( _ ) >> light.save()
		when:
			def result = controller.changeLight(light.id)
		then:
			view == "/light/show"		
	}
}