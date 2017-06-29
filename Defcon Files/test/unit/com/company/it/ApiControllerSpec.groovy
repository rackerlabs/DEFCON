//location1 is the name of one of the locations.

package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.*

@TestFor(ApiController)
@Mock([LightService])
class ApiControllerSpec extends Specification {
	
	LightService service
		
	def setup() {
		service = Mock()
		controller.lightService = service
	}

	def "light - updates light color provided correct id & color code"() {
		given:
			service.changeLight( _ ) >> null
		when:
			request.JSON = '{ "id": "1", "color": "green" }'
			controller.light()
		then:
			response.status == response.SC_OK
		and: 
			response.text == "Light Updated"
	}

	def "light - returns error code when exception occurs"() {
		given:
			service.changeLight( _ ) >> { throw new Exception() }
		when:
			request.JSON = '{ "id": "1", "color": "green" }'
			controller.light()
		then:
			response.status == response.SC_CONFLICT
	}

	def "lights - updates multiple lights when JSON contains valid data"() {
		given:
			service.changeLights( _ ) >> null
		when:
			request.JSON = '{"lights" : [{ "id": "1", "color": "green" },{ "id": "2", "color": "green" }]}'
			def result = controller.lights()
		then:
			response.status == response.SC_OK
		and: 
			response.text == "Lights Updated"
	}

	def "lights - returns error code when exception occurs"() {
		given:
			service.changeLights( _ ) >> { throw new Exception() }
		when:
			request.JSON.lights = ''
			controller.lights()
		then:
			response.status == response.SC_CONFLICT
		and:
			response.text == "Error Updating Lights(null)"
	}

	def "location - updates the location provided location name"() {
		given:
			service.changeLocation( _ ) >> null
		when:
			request.JSON = '{ name: "location1" }'
			controller.location()
		then:
			response.status == response.SC_OK
		and:
			response.text == "Location Updated"
	}

	def "location - returns error code when exception occurs"() {
		given:
			service.changeLocation( _ ) >> { throw new Exception() }
		when:
			request.JSON = '{ name: "location1" }'
			controller.location()
		then:
			response.status == response.SC_CONFLICT
		and:
			response.text == "Error Updating Location"
	}

	def "locations - updates all the locations provided locations"() {
		given:
			service.changeLocations( _ ) >> null
		when:
			request.JSON = '{name: "location1" }'
			controller.locations()
		then:
			response.status == response.SC_OK
		and:
			response.text == "Locations Updated"
	}

	def "locations - returns error code when exception occurs"() {
		given:
			service.changeLocations( _ ) >> { throw new Exception() }
		when:
			request.JSON = '{ name: "location1" }'
			controller.locations()
		then:
			response.status == response.SC_CONFLICT
		and:
			response.text == "Error Updating Locations(null)"
	}

	def "all - updates all the lights"() {
		given:
			service.changeAll( _ ) >> null
		when:
			request.JSON = '{ id: 1, color: "green", name: "location1"}'
			controller.all()
		then:
			response.status == response.SC_OK
		and:
			response.text == "All Lights Updated"
	}

	def "all - returns error code when exception occurs"() {
		given:
			service.changeAll( _ ) >> { throw new Exception() }
		when:
			request.JSON = '{ id: 1, color: "green", name: "location1"}'
			controller.all()
		then:
			response.status == response.SC_CONFLICT
		and:
			response.text == "Error Updating All Lights(null)"
	}

	def "status - returns the light status"() {
		given:
			service.statusAll() >> [ new Location(name:"Location1") ]
		when:
			controller.status()
		then:  
			response.text == "[{"
	}
}