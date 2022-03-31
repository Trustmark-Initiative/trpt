package edu.gatech.gtri.trustmark.trpt.init

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import grails.compiler.GrailsCompileStatic
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

@GrailsCompileStatic
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
