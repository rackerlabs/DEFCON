package com.rackspace.it

/**
 * Light here stands for the Light Controller or PLC and not an 
 * individual light.
 */

class Light {

    Integer port
    Integer net
    Integer node
    Integer unit
    String  name
    String  ip
    Boolean active = false
    Color color

    static belongsTo = [location: Location]
    static constraints = {
        name blank:false
        ip   blank:false
        color blank:false, nullable:false
        port min:1, max: 55901
        node min: 0, max: 10
        net min: 0, max: 10
    }

    static mapping = {
        version false
		sort "name"
    }

	void setColor(String color) { this.color = Color.get(color) }
	void setColor(Color color) { this.color = color }

	String toString() { name }	
}