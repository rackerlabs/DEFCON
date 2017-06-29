import com.rackspace.it.*
import grails.util.Environment
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class BootStrap {

    def lightMap 
		
	def init = { servletContext ->
	    if ( Environment.current != Environment.PRODUCTION ) {
			lightMap = [port: 9600, net: "01", node: "01", unit: "01", ip: "127.0.0.1", active: true, color: Color.GREEN] //use this when talking with the Virtual PLC			
	        createUser(createRole())
	        //createLocations() 
		} else if (Role.count() == 0) {
			//ip address need populating
			lightMap = [port: 9600, net: "01", node: "00", unit: "00", ip: "XXX.XXX.XXX.XXX", active: true, color: Color.GREEN]  //use this when trying to talk with the real PLC
			createUser(createRole())
			//createLocations()
		}
	}

    def createLocations() {
        /*def json = new JsonBuilder()
        def lightFile = new File('/grails-app/conf/spring/', 'lights.json')

        if (lightFile.exists()) {
            new File('/grails-app/conf/spring/lights.json').eachLine { line ->
                def jsonObj = new JsonSlurper().parseText(line)
                def location = new Location(name: jsonObj.location)
                lightMap.name = jsonObj.name
                lightMap.net = jsonObj.net
                lightMap.node = jsonObj.node
                lightMap.port = jsonObj.port
                lightMap.unit = jsonObj.unit
                lightMap.ip = jsonObj.ip
                lightMap.active = jsonObj.active
                lightMap.color = Color.(jsonObj.color)
                location.addToLights(new Light(lightMap))
                location.save()
            }

        } else if (!lightFile.exists()) {
            //lightFile.createNewFile()
        }*/

        def location1 = new Location(name:"Location1")
            lightMap.name = "Location1 Phase 1"
            lightMap.net = "00"
            lightMap.node = "00"
            lightMap.unit = "00"
			//add IP Address
            lightMap.ip = "XXX.XXX.XXX.XXX"
            location1.addToLights( new Light(lightMap) )
            location1.save()

        def location2 = new Location(name:"Location2")
            lightMap.name = "Location2 Light"
            lightMap.net = "00"
            lightMap.node = "00"
            lightMap.unit = "00"
			//add IP Address
            lightMap.ip = "XXX.XXX.XXX.XXX"
            location2.addToLights( new Light(lightMap) )
	        location2.save()

        def testLight = new Location(name:"Test Light")
            lightMap.name = "Test PLC"
            lightMap.net = "00"
            lightMap.node = "00"
            lightMap.unit = "00"
			//add IP Address
            lightMap.ip = "XXX.XXX.XXX.XXX"
            testLight.addToLights(new Light(lightMap))
            testLight.save()
    }

	def createRole() {
		def role  = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save(failOnError: true)
		return role
	}
	

    def createUser(role) {
		//Update to add User Name and Password
		def user = User.findByUsername("USER") ?: new User(
            username: "USER",
            password: "PASSWORD",
            enabled: true).save(failOnError: true)
		if (!user.authorities.contains(role)) {
			UserRole.create user, role
		}
    }

    def destroy = {
    }
}
