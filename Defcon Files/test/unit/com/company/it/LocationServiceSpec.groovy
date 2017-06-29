package com.rackspace.it

import grails.test.mixin.*
import spock.lang.Specification
import grails.validation.ValidationException

@TestFor(LocationService)
@Mock([Location, Location])
class LocationServiceSpec extends Specification {
	
	def locationMap = [name: "Location3!"]

	def "get - returns record when one exists"() {
		when:
			new Location(locationMap).save()
		then:
			service.get(1) != null
	}
	
	def "get - returns null when id does not exist"() {
		when:
			def location = new Location(locationMap).save()
		then:
			service.get(100) == null
	}
	
	def "save - returns a new location when valid data is provided"() {
		when:
			def location = service.save(locationMap)
		then:
			location instanceof Location
		and:
			location.id == 1
	}
	
	def "save - returns error when invalid data is provided"() {
		when:
			def location = service.save([:])
		then:
			thrown(ValidationException)
	}

	def "save - returns existing location when an exsting id and valid date is provided"() {
		given:
			def location = new Location(locationMap).save()
			locationMap.id = location.id
		when:
			def result = service.save(locationMap)
		then:
			locationMap.id == result.id
	}
	
	def "list - returns no more locations than specified in max parameter"() {
		when:
			for(i in 1..25){ new Location(locationMap).save() }
		then: "defaults to 20 locations"
			service.list().size() == 20
		and: "returns 5 when max 5"
			service.list([max:5]).size() == 5
		and: "returns 25 when max 25"
			service.list([max:25]).size() == 25
	}
	
	def "delete - returns true when valid id is provided"() {
		when:
			def id = new Location(locationMap).save().id
		then:
			service.delete(id) == null	
	}
	
	def "delete - throws exception when invalid id is provided"() {
		when:
			service.delete(111)
		then:
			thrown(NullPointerException)
	}

	def "validate - returns true if given a good data"() {
		when:
			def result = service.validate(locationMap)
		then:
			result == true
	}

	def "count - returns the number of Locations"() {
		given:
			for(i in 1..10){ new Location(name:"Location1"+i).save() }
		when:
			def result = service.count()
		then:
			result == 10
	}
}
