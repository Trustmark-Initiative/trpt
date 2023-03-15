// ---------------------------------------------------------------------------------------------------------------------
// CONFIGURATION FROM GRAILS
// ---------------------------------------------------------------------------------------------------------------------
// See 4.1.2 Built in options https://docs.grails.org/latest/guide/conf.html#builtInOptions
// ---------------------------------------------------------------------------------------------------------------------
// Sets the default encoding regime for GSPs - can be one of 'none', 'html', or 'base64' (default: 'none'). To reduce
// risk of XSS attacks, set this to 'html'.

grails.views.default.codec = 'html'

// See 4.4 The DataSource https://docs.grails.org/latest/guide/conf.html#dataSource
// ---------------------------------------------------------------------------------------------------------------------
// driverClassName - The class name of the JDBC driver
//
// username - The username used to establish a JDBC connection
//
// password - The password used to establish a JDBC connection
//
// url - The JDBC URL of the database
//
// dbCreate - Whether to auto-generate the database from the domain model - one of 'create-drop', 'create', 'update',
// 'validate', or 'none'

dataSource {
    driverClassName = 'com.mysql.cj.jdbc.Driver'
    url = 'jdbc:mysql://trptDatabase:3306/trpt_db?verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true'
    username = 'trpt_user'
    password = 'trpt_pw_11'
}

environments {
    development {
        // required for development server to locate git.properties file
        spring.info.git.location = 'file:build/resources/main/git.properties'

        dataSource {
            dbCreate = 'update'
            properties {
                testWhileIdle = true
                dbProperties {
                    autoReconnect = true
                }
            }
        }
    }
    test {
        dataSource {
            dbCreate = 'update'
            properties {
                testWhileIdle = true
                dbProperties {
                    autoReconnect = true
                }
            }
        }
    }
    production {
        dataSource {
            dbCreate = 'update'
            properties {
                testWhileIdle = true
                dbProperties {
                    autoReconnect = true
                }
            }
        }
    }
}

// See 8.1.9 Uploading Files https://docs.grails.org/latest/guide/theWebLayer.html#uploadingFiles
// ---------------------------------------------------------------------------------------------------------------------

grails.controllers.upload.maxFileSize = 128000
grails.controllers.upload.maxRequestSize = 128000

// See 8.6 Content Negotiation https://docs.grails.org/latest/guide/theWebLayer.html#contentNegotiation
// ---------------------------------------------------------------------------------------------------------------------

grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
        all          : '*/*',
        atom         : 'application/atom+xml',
        css          : 'text/css',
        csv          : 'text/csv',
        form         : 'application/x-www-form-urlencoded',
        html         : ['text/html', 'application/xhtml+xml'],
        js           : 'text/javascript',
        json         : ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss          : 'application/rss+xml',
        text         : 'text/plain',
        hal          : ['application/hal+json', 'application/hal+xml'],
        xml          : ['text/xml', 'application/xml']
]

// See 17.2 Cross Site Scripting (XSS) Prevention https://docs.grails.org/latest/guide/security.html#xssPrevention
// ---------------------------------------------------------------------------------------------------------------------
// expression - The expression codec is used to encode any code found within ${..} expressions. The default for newly
// created application is html encoding.
//
// scriptlet - Used for output from GSP scriplets (<% %>, <%= %> blocks). The default for newly created applications is
// html encoding
//
// taglib - Used to encode output from GSP tag libraries. The default is none for new applications, as typically it is
// the responsibility of the tag author to define the encoding of a given tag and by specifying none Grails remains
// backwards compatible with older tag libraries.
//
// staticparts - Used to encode the raw markup output by a GSP page. The default is none.

grails.views.gsp.encoding = 'UTF-8'
grails.views.gsp.htmlcodec = 'xml'
grails.views.gsp.codecs.expression = 'html'
grails.views.gsp.codecs.scriptlet = 'html'
grails.views.gsp.codecs.taglib = 'none'
grails.views.gsp.codecs.staticparts = 'none'

// See 21.3 Deployment Configuration Tasks https://docs.grails.org/latest/guide/deployment.html#deploymentTasks
// ---------------------------------------------------------------------------------------------------------------------

// grails certificate, key, and keystore
// openssl req -x509 -newkey rsa:4096 -keyout grails-app/conf/gamest-api-registry.key.pem -out grails-app/conf/gamest-api-registry.crt.pem -days 365 -subj "/CN=gamest-api-registry"
// openssl pkcs12 -export -inkey grails-app/conf/gamest-api-registry.key.pem -in grails-app/conf/gamest-api-registry.crt.pem -out grails-app/conf/gamest-api-registry-keystore.p12 -name gamest-api-registry
// keytool -importkeystore -srckeystore grails-app/conf/gamest-api-registry-keystore.p12 -srcstoretype pkcs12 -destkeystore grails-app/conf/gamest-api-registry-keystore.jks -deststoretype jks

// server.port = 8443
// server.ssl['key-store'] = 'classpath:gamest-api-registry-keystore.jks'
// server.ssl['key-store-type'] = 'JKS'
// server.ssl['key-store-password'] = 'password'
// server.ssl['key-alias'] = 'gamest-api-registry'
// server.ssl['key-password'] = 'password'

// ---------------------------------------------------------------------------------------------------------------------
// CONFIGURATION FROM SPRING
// ---------------------------------------------------------------------------------------------------------------------
// See 2. Endpoints https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints
// ---------------------------------------------------------------------------------------------------------------------

management.endpoints['enabled-by-default'] = false
management.endpoint.health.enabled = true
management.endpoint.info.enabled = true
management.endpoints.web.exposure.include = 'health,info'

// See Appendix A: Common Application Properties https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#application-properties
// ---------------------------------------------------------------------------------------------------------------------

spring.main['banner-mode'] = 'console'

// See 8. Developer Tools https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools
// ---------------------------------------------------------------------------------------------------------------------

spring.devtools.restart['additional-exclude'] = ['*.gsp', '**/*.gsp']

// ---------------------------------------------------------------------------------------------------------------------
// CONFIGURATION FROM GORM
// ---------------------------------------------------------------------------------------------------------------------
// See GORM for Hibernate https://gorm.grails.org/6.0.x/hibernate/manual/
// ---------------------------------------------------------------------------------------------------------------------

hibernate {
    cache {
        use_second_level_cache = false
    }
}

info {
   app {
     name = '@info.app.name@'
     version = '@info.app.version@'
     grailsVersion = '@info.app.grailsVersion@'
   }
}


