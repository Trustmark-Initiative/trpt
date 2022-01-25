import edu.gatech.gtri.trustmark.trpt.listener.AuthFailureSecurityListener
import edu.gatech.gtri.trustmark.trpt.listener.AuthSuccessSecurityListener
import edu.gatech.gtri.trustmark.trpt.listener.UserPasswordEncoderListener
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties

beans = {
    applicationProperties(ApplicationProperties.class)
    userPasswordEncoderListener(UserPasswordEncoderListener.class)
    authFailureListener(AuthFailureSecurityListener.class)
    authSuccessListener(AuthSuccessSecurityListener.class)
}
