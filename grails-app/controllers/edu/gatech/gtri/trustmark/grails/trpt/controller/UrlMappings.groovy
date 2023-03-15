package edu.gatech.gtri.trustmark.grails.trpt.controller

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'index', view: '/index')
        "500"(view: '/error/500')
        "404"(view: '/error/404')
        "403"(view: '/error/403')
    }
}
