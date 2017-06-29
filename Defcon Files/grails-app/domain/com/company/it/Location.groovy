package com.rackspace.it

class Location {
    
    String name

    static hasMany = [lights:Light]

    static constraints = {
        name blank:false
    }
    
    static mapping = {
        version false
		sort "name"
    }
    
    String toString() { name }
}
