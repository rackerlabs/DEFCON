package com.rackspace.it

class LocationService {
	
    def get(id) {
        Location.get(id)
    }

    def save(map, failOnError = true) {
        def Location = ( map.id ) ? Location.get(map.id) : new Location()

        Location.properties = map
        Location.save(failOnError:failOnError)

        Location
    }
    
    def validate(map) {
        new Location(map).validate()
    }
    
    def list(params = [max: '20'] ) {
        Location.list(params)
    }
    
    def count() {
        Location.count()
    }

    def delete(id) {
        def Location = Location.get(id)
        Location.delete()
    }
}
