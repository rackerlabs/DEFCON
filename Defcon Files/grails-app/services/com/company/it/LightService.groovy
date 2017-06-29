package com.rackspace.it

class LightService {

    def get(id) {
        Light.get(id)
    }

    def save(map, failOnError = true) {
        def light = ( map.id ) ? Light.get(map.id) : new Light()

        light.properties = map
        light.save(failOnError:failOnError)

        light
    }
    
    def validate(map) {
        new Light(map).validate()
    }
    
    def list(params = [max: '20'] ) {
        Light.list(params)
    }
    
    def count() {
        Light.count()
    }

    def delete(id) {
        def light = Light.get(id)
        light.delete()
    }

    def changeLocation(map) {
        def color = Color.get(map.color)
        def location = (map.id) ? Location.get(map.id) : Location.findByNameIlike(map.name)

        location.lights?.each { light ->
            //light.color = color  - this was causing problems setting the color for inactive lights
            changeLight(light, color)
        }
        location
    }

//the following function takes a map of lights and a color to set the lights to
    def changeLight(map, color='') throws Exception { 
        def light = Light.get(map.id)
        
		if (light.active == true && color != '') {
            light.setColor(color)
			sendCommand(light)
            light.save()
		} else if(light.active == true) {
            light.color = map.color
            sendCommand(light)
            light.save()
        }
//not sure if I want the else, because would still want the active
//lights to be updated and the inactive left alone
        /*else if(light.active.toBoolean() == false){
            //println("Light is Inactive")
			throw new Exception("Light is Inactive")
		}*/
    }

    def changeLights(list) throws Exception {
        def results = []
		
        list?.each() { light ->
            results << changeLight(light)
        }
		
        results
    }

    def changeLocations(map) throws Exception {
        def results
        map?.each() {
         changeLocation(it)
        }
    }

        // "80"    /* ICF (Information Control Field) */
        // "00"    /* RSV (Reserved) */
        // "02"    /* GCT (Gateway Count) */
        // "01"    /* Destination PLC Net Address */
        // "04"    /* Destination PLC Node Address */
        // "00"    /* Destination PLC Unit Address */
        // "01"    /* Local Net Address */
        // "00"    /* Local Node Address */
        // "00"    /* Local Unit Address */
        // "00"    /* SID (Source ID) */
        // "0102"  /* Command Code */
        // "30"    /* Command Memory Area */
        // "00c8"  /* Light Channel (200 as HEX) */
        // "00"    /* Color Code */
        // "0001"  /* Number of Items */
        // "00"   /* Lights On(01) or Off(01) */

    def sendCommand(light) throws Exception {
        def buffer
        def list = getColorSequence(Color.get("clear")) + getColorSequence(light.color) + getColorSequence(light.color)
        def list2 = getColorSequence(light.color) + getColorSequence(light.color)
        try {
                list.each() {
                    def color_code = it.color_code

                    buffer  = "800002"
                    buffer += String.format("%02d", light.net)
                    buffer += String.format("%02d", light.node)
                    buffer += String.format("%02d", light.unit)
                    buffer += "0003000001023000c8" + it.color_code + "0001" + it.status
                    println("Color code = " + it.color_code + "   On/Off = " + it.status + "    Light = " + light.name)
                    def data = buffer.decodeHex()
                    def addr = InetAddress.getByName(light.ip)
                    def port = light.port
                    def socket = new DatagramSocket()
                    def packet = new DatagramPacket(data, data.length, addr, port)
                    socket.send(packet)
                
                    // Prepare the packet for receive
                    packet.data = new byte[100]
                    // Wait for a response from the server
                    socket.soTimeout = 2000
                    socket.receive( packet )

                    def response = packet.getData().encodeHex().toString()
                    //updated the substring, after looking at packet capture found that we were not looking at the full response completion code
                    //now we will check to make sure the completion code is 0000 - which is a normal completion
                    if (response.substring(20,28) != "01020000" ) {
                        println ("Error thrown = " + response)
                        throw new Exception("Error returned from PLC")
                    }
            } 
        }
        catch (Exception e) {
            println("error: " + e)
            if (e.message == "Receive timed out") {
                println("A Receive timed out occured trying to updated lights again")
                list2.each() {
                    def color_code = it.color_code

                    buffer  = "800002"
                    buffer += String.format("%02d", light.net)
                    buffer += String.format("%02d", light.node)
                    buffer += String.format("%02d", light.unit)
                    buffer += "0003000001023000c8" + it.color_code + "0001" + it.status
                    println("Color code = " + it.color_code + "   On/Off = " + it.status + "    Light = " + light.name)
                    def data = buffer.decodeHex()
                    def addr = InetAddress.getByName(light.ip)
                    def port = light.port
                    def socket = new DatagramSocket()
                    def packet = new DatagramPacket(data, data.length, addr, port)
                    socket.send(packet)
                
                    // Prepare the packet for receive
                    packet.data = new byte[100]
                    // Wait for a response from the server
                    socket.soTimeout = 2000
                    socket.receive( packet )

                    def response = packet.getData().encodeHex().toString()
                    //updated the substring, after looking at packet capture found that we were not looking at the full response completion code
                    //now we will check to make sure the completion code is 0000 - which is a normal completion
                    if (response.substring(20,28) != "01020000" ) {
                        println ("Error thrown = " + response)
                        throw new Exception("Error returned from PLC")
                    } 
                }
                //return "A socket timeout exception occured."
            } else {
                println("Received the following error " + e.message + ". Trying to update lights again.")
                list2.each() {
                    def color_code = it.color_code

                    buffer  = "800002"
                    buffer += String.format("%02d", light.net)
                    buffer += String.format("%02d", light.node)
                    buffer += String.format("%02d", light.unit)
                    buffer += "0003000001023000c8" + it.color_code + "0001" + it.status
                    println("Color code = " + it.color_code + "   On/Off = " + it.status + "    Light = " + light.name)
                    def data = buffer.decodeHex()
                    def addr = InetAddress.getByName(light.ip)
                    def port = light.port
                    def socket = new DatagramSocket()
                    def packet = new DatagramPacket(data, data.length, addr, port)
                    socket.send(packet)
                
                    // Prepare the packet for receive
                    packet.data = new byte[100]
                    // Wait for a response from the server
                    socket.soTimeout = 2000
                    socket.receive( packet )

                    def response = packet.getData().encodeHex().toString()
                    //updated the substring, after looking at packet capture found that we were not looking at the full response completion code
                    //now we will check to make sure the completion code is 0000 - which is a normal completion
                    if (response.substring(20,28) != "01020000" ) {
                        println ("Error thrown = " + response)
                        throw new Exception("Error returned from PLC")
                    } 
                }
            }
        }
    }

    def getColorSequence(color) {
        def green_status  = (color.text == "green" || color.text == "all")  ? "01" : "00"
        def blue_status   = (color.text == "blue" || color.text == "all")   ? "01" : "00"
        def yellow_status = (color.text == "yellow" || color.text == "all") ? "01" : "00"
        def red_status    = (color.text == "red" || color.text == "all")    ? "01" : "00"

        return [
            [color_code: Color.get("green").plc_code, status: green_status], 
            [color_code: Color.get("blue").plc_code, status: blue_status], 
            [color_code: Color.get("yellow").plc_code, status: yellow_status],
            [color_code: Color.get("red").plc_code, status: red_status]
        ]
    }

    def changeAll(color) {
        def locations = Location.findAll()
            locations?.each { loc -> 
                changeLocation([name:loc.name, color: color])
            }

        locations
     }

    def statusAll() {
        def locations = Location.findAll()
        locations
     }
}
