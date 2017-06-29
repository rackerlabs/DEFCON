class UrlMappings {

    static mappings = {
        "/api/light/$id/color/$color" (controller:"api", action: "light")
        "/api/lights" (controller:"api", action: "lights")
        "/api/location/$name/color/$color" (controller:"api", action: "location")
        "/api/locations" (controller:"api", action: "locations")
        "/api/all/color/$color" (controller:"api", action: "all")
        "/api/status" (controller:"api", action: "status")

        "/$controller/$action?/$id?" ()
        "/"(action:"list", controller:"location")
        "500"(view:'/error')
    }
}