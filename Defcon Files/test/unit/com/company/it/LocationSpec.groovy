package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Location)
class LocationSpec extends Specification {

	def "Validate constraints"() {
		when: 
			def loc = new Location(name: name)
			loc.validate()
		then:
			loc.hasErrors() == hasErrors
		where:
			name		| hasErrors
			"Location1"	| false
			""			| true
			null		| true
	}
	
	def 'validate mappings'() {
		setup:
			def methods = [:]
			def methodMissingClosure = { String name, Object args -> methods."$name" = args as List }
			def loc = new Location()
			loc.mapping.metaClass.methodMissing = methodMissingClosure
		when: 
			loc.mapping()
		then:
			methods.version.first() == false
	}	

	def 'validate toString returns name'() {
		when: 
			def loc = new Location(name: name)
		then:
			"$loc" == name
		where:
			name << ["Location1", "Location2"]
	}
}