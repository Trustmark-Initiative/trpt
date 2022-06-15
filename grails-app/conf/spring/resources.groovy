import edu.gatech.gtri.trustmark.trpt.listener.UserPasswordEncoderListener
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties

beans = {
    applicationProperties(ApplicationProperties.class)
    userPasswordEncoderListener(UserPasswordEncoderListener.class)
}
