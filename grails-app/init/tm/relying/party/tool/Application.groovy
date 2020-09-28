package tm.relying.party.tool

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

import groovy.transform.CompileStatic
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

class Application extends GrailsAutoConfiguration implements org.springframework.context.EnvironmentAware  {
    static void main(String[] args) {
        info("Starting grails application...")
        GrailsApp.run(Application, args)
    }

    @Override
    void setEnvironment(Environment environment) {
        def res = getClass().classLoader.getResourceAsStream('trpt_config.properties')
        if( res ) {
            Properties props = new Properties()
            props.load(res)
            environment.propertySources.addFirst( new MapPropertySource('trpt_config.properties', props) )
        }
    }

    private static final void info(String msg){
        System.out.println(msg)
        System.out.flush()
    }
}