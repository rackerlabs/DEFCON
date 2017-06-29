package com.rackspace.it

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Light)
class ColorSpec extends Specification {
	
	def "get - validate colors are returned appopriately"() {
		expect:
			Color.get(colorString) == color
		where: 
			colorString	| color
			"clear"		| Color.CLEAR
			"green"		| Color.GREEN
			"blue"		| Color.BLUE
			"yellow"	| Color.YELLOW
			"red"		| Color.RED
			"all"		| Color.ALL
	}
	
	def "get - validate exception thrown with invalid data"() {
		when:
			Color.get(colorString)
		then:
			thrown(IllegalArgumentException)
		where:
			colorString = ["test", " ", null, "testing", 1, [:] ]
	}
}