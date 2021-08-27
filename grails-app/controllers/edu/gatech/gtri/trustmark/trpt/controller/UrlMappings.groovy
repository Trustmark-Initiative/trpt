package edu.gatech.gtri.trustmark.trpt.controller

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'index', view: '/index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
