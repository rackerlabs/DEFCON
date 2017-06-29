package com.rackspace.it

import grails.test.mixin.*
import spock.lang.Specification
import grails.validation.ValidationException

@TestFor(LightService)
@Mock([Light, Location])
class LightServiceSpec extends Specification {

	def lightMap = [ port: 1, net: 1, node: 1, unit: 1, name: "Red Light", ip: "1.1.1.1", active: true, 
		color: Color.GREEN, location: new Location(name:"Location3") ]

	def badLightMap = [port: 1, net: 1, node: 1, unit: 1, name: "Test Light", ip: "1.1.1.1", active: true,
		color: "test"]	

	def "get - returns record when one exists"() {
		when:
			new Light(lightMap).save()
		then:
			service.get(1) != null
	}
	
	def "get - returns null when id does not exist"() {
		when:
			def light = new Light(lightMap).save()
		then:
			service.get(100) == null
	}
	
	def "save - returns a new light when valid data is provided"() {
		when:
			def light = service.save(lightMap)
		then:
			light instanceof Light
		and:
			light.id == 1
	}
	
	def "save - returns error when invalid data is provided"() {
		when:
			def light = service.save([:])
		then:
			thrown(ValidationException)
	}

	def "save - returns existing light when an exsting id and valid date is provided"() {
		given:
			def light = new Light(lightMap).save()
			lightMap.id = light.id
			lightMap.port= 100
		when:
			def result = service.save(lightMap)
		then:
			lightMap.id == result.id
		and:
			lightMap.port == result.port
	}
	
	def "list - returns no more lights than specified in max parameter"() {
		when:
			for(i in 1..25){ new Light(lightMap).save() }
		then: "defaults to 20 lights"
			service.list().size() == 20
		and: "returns 5 when max 5"
			service.list([max:5]).size() == 5
		and: "returns 25 when max 25"
			service.list([max:25]).size() == 25
	}
	
	def "delete - returns true when valid id is provided"() {
		when:
			def id = new Light(lightMap).save().id
		then:
			service.delete(id) == null	
	}
	
	def "delete - throws exception when invalid id is provided"() {
		when:
			service.delete(111)
		then:
			thrown(NullPointerException)
	}
	
	def "changeLight - validate light method returns successfully"() {
		given:
			def light = new Light(lightMap)
			light.save()
		and:
			lightMap.color = Color.RED
			lightMap.id = light.id
			
			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map, bool -> light }			
		when:
			def result = service.changeLight(lightMap)
		then:
			result instanceof Light
		and:
			result.color == lightMap.color	
	}

	def "changeLight - validate light method returns successfully when sent a map and a color"() {
		given:
			def light = new Light(lightMap)
			light.save()
		and:
			lightMap.active = true
			lightMap.id = light.id

			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map, bool -> light }	
		when:
			def result = service.changeLight(lightMap, Color.RED)
		then:
			result instanceof Light
		//and:
		//	result.color == Color.RED
	}

	def "changeLight - validate light method returns successfully when sent a map and a color"() {
		given:
			def light = new Light(lightMap)
			light.save()
		and:
			lightMap.color = Color.RED
			lightMap.active = true
			lightMap.id = light.id

			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map, bool -> light }	
		when:
			def result = service.changeLight(lightMap, '')
		then:
			result instanceof Light
		and:
			result.color == lightMap.color
	}

	/*def "changeLight - exits the function if the light isn't active"() {
		given:
			def light = new Light(lightMap)
			light.save()
		and:
			lightMap.active = false
			lightMap.id = light.id

			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map, bool -> light}
		when:
			def result = service.changeLight(lightMap, '')
		then:
			//result == lightMap.active
			lightMap.active == false
	}

	/* we are no longer throwing an error if the light is not active, therefor commenting out this code
	def "changeLight - validate light method returns error if Light is not active"(){
		given:
			def light = new Light(lightMap)
			light.active = false
			light.save()
		and:
			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map, bool -> light }	
		when:
			def result = service.changeLight(light)
		then:
			thrown(java.lang.Exception)
	}*/

	def "changeLights - validate light method returns successfully"() {
		given:
			service.metaClass.changeLight = { map -> map.get(map.id) }
		when:
			def result = service.changeLights([ [1: "Testing"] ])
		then:
			result instanceof List
	}

	def "changeLocation - validate location method returns successfully"() {
		given: "A location exists with one light associated with it"
			def location = new Location(name:"Location1")
			def light = new Light(lightMap)
			location.addToLights(light)
			location.save(failOnError:true)			
		and:
			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map -> light }
		when:
			def result = service.changeLocation([name:"Location1", color: "GREEN"])			
		then:
			result instanceof Location
		and:
			location.lights.size() == 1
	}

	def "changeLocation - validate location method returns fail given a bad light property"(){
		given: "A location exists with one light associated with it"
			def location = new Location(name: "Location1")
			def light = new Light(lightMap)
			location.addToLights(light)
			location.save(failOnError:true)
		when:
			def result = service.changeLocation([name:"Location1", color: "test"]) 
		then:
			thrown(IllegalArgumentException)
	}

	def "changeLocation - validate location method returns fail given light doesn't exist"(){
		when: "Location does not exist"
			def result = service.changeLocation([name:"don't exist", color:"nothing"])
		then:
			thrown(IllegalArgumentException)
	}

	def "changeAll - validate changeAll method returns successfully"() {
		given: "A location exists with one light associated with it"
			def location = new Location(name: "Location1")
			def light = new Light(lightMap)
			location.addToLights(light)
			location.save(failOnError:true)
		and:
			service.metaClass.sendCommand = { map -> }
			service.metaClass.save = { map -> light }
		when: "Call the method changeAll"
			def result = service.changeAll("red")
		then: "Returns a list of all locations, currently Location1 for testing"
			result.size() == Location.count()
			result.name.contains("Location1")
		and: "The location contains the light(s)"
			result.lights.size() == light.count()
		// Need to add a test to check to make sure the color is set appropriately
	}

	def "validate - make sure that it returns successfully given good data"() {
		when:
			def result = service.validate(lightMap)
		then:
			result == true
	}

	def "count - returns the amount of lights"() {
		given:
			for(i in 1..10){ new Light(lightMap).save() }
		when:
			def result = service.count()
		then:
			result == 10
	}

	def "statusAll - returns all the locations"() {
		given:
			for(i in 1..10){ new Location(name:"Location1"+i).save() }
		when:
			def result = service.statusAll()
		then:
			result.size() == 10
	}

	def "getColorSequence - returns the color code based on the color string"() {
		given:"We create a new light with color set to green"
			def light =  new Light(lightMap).save()
		when:
			def result = service.getColorSequence(light.color)
		then:
			result.size() != 0 
		and:"Here we check that the status returns 01 for green"
			result[0].status == "01"
	}
	
	def "sendCommand - success"() {
		given: 
			def colorSeq = [
				[color_code: Color.get("green").plc_code, status: "00"], 
	            [color_code: Color.get("blue").plc_code, status: "00"], 
	            [color_code: Color.get("yellow").plc_code, status: "00"],
	            [color_code: Color.get("red").plc_code, status: "00"] ]
				
				service.metaClass.getColorSequence = { map -> colorSeq }
		and:
			DatagramSocket.metaClass.static.send = { DatagramPacket packet -> }
			DatagramSocket.metaClass.static.receive = { DatagramPacket packet -> }
		and:
			service.metaClass.encodeHex = { -> "1234567890123456789001020000" }
			DatagramPacket.metaClass.static.getData = { -> service }
		when:
			def result = service.sendCommand(lightMap)
		then: "Returns the list of color sequences"
			result instanceof List		
	}

	def "sendCommand - failure"() {
		given: 
			def colorSeq = [
				[color_code: Color.get("green").plc_code, status: "00"], 
	            [color_code: Color.get("blue").plc_code, status: "00"], 
	            [color_code: Color.get("yellow").plc_code, status: "00"],
	            [color_code: Color.get("red").plc_code, status: "00"] ]
				
				service.metaClass.getColorSequence = { map -> colorSeq }
		and:
			DatagramSocket.metaClass.static.send = { DatagramPacket packet -> }
			DatagramSocket.metaClass.static.receive = { DatagramPacket packet -> }
		and:
			service.metaClass.encodeHex = { -> "1234567890123456789001021005" }
			DatagramPacket.metaClass.static.getData = { -> service }
		when:
			def result = service.sendCommand(lightMap)
		then:
			Exception e = thrown()
			e.message == "Error returned from PLC"
	}

	def "sendCommand - failure and goes into the catch statement"() {
		given: 
			def colorSeq = [
				[color_code: Color.get("green").plc_code, status: "01"], 
	            [color_code: Color.get("blue").plc_code, status: "01"], 
	            [color_code: Color.get("yellow").plc_code, status: "01"],
	            [color_code: Color.get("red").plc_code, status: "01"] ]
	        
			service.metaClass.getColorSequence = { map -> colorSeq }
			DatagramSocket.metaClass.static.send >> {DatagramPacket packet -> }
			service.metaClass.encodeHex = { -> "1234567890123456789001020000" }
			DatagramPacket.metaClass.static.getData = { -> service }
			DatagramSocket.metaClass.static.soTimeout >> {throw new SocketTimeoutException()}
		when:
			def result = service.sendCommand(lightMap)
		then:
			SocketTimeoutException e = thrown()
			e.message == "Receive timed out"
	}

	
	def "changeLocations - updates all the specified locations"() {
		given:
			service.metaClass.changeLocation = { map -> map.get(map.id) }
		when:
			def result = service.changeLocations([ [1: "Testing"] ])
		then:
			result instanceof List
	}
	
}