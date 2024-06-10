package edu.gatech.gtri.trustmark.grails.trpt.init

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

@SpringBootApplication
@ComponentScan("edu.gatech.gtri.trustmark.grails.oidc")
class Application extends GrailsAutoConfiguration implements org.springframework.context.EnvironmentAware {

    static void main(final String[] argumentArray) {
        GrailsApp.run(Application, argumentArray)
    }

    @Override
    void setEnvironment(final Environment environment) {
        final Properties properties = new Properties()
        final InputStream inputStream = getClass().classLoader.getResourceAsStream('trpt.properties')
        if (inputStream) {
            properties.load(inputStream)
        } else {
            throw new RuntimeException('The system could not find "trpt.properties".')
        }
        ((ConfigurableEnvironment) environment).getPropertySources().addFirst(new MapPropertySource('trpt.properties', (Map<String, Object>) (Map) properties));
    }
}
