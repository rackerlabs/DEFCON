package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Light)
class LightSpec extends Specification {

	def "Validate name constraint"() {
		when:
			def light = new Light(name: name, port: port, ip: ip, net: net, node: node, unit: unit, active: active, color: color, location: location)
			light.validate()
		then:
			light.hasErrors() == hasErrors
		where:
			name		| port  | ip	| net	| node	| unit	| active| color 				| location 						| hasErrors
			"Location1"	| 123 	| 123	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| false
			""			| 123	| 123	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			null		| 123	| 123	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| null  | 123 	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| null	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| ""	| 1		| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| null	| 1		| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| 1		| null	| 123	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| 1		| 1		| null	| true	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| 1		| 1		| 123	| null	| Color.GREEN 			| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| 1		| 1		| 123	| true	| null					| new Location(name:"Location3") 	| true
			"Location1"	| 123	| 123	| 1		| 1		| 123	| true	| Color.GREEN			| null						 	| true
	}

	def 'validate mappings'() {
		setup:
			def methods = [:]
			def methodMissingClosure = { String name, Object args -> methods."$name" = args as List}
			def light = new Light()
			light.mapping.metaClass.methodMissing = methodMissingClosure
		when:
			light.mapping()
		then:
			methods.version.first() == false
	}

	def 'validate toString returns name'() {
		when:
			def light = new Light(name: name)
		then:
			"$light" == name
		where:
			name << ["Location1 Tower", "Location2 Tower"]
	}
}