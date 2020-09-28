import tm.relying.party.tool.UserPasswordEncoderListener
import tm.relying.party.tool.util.AuthFailureSecurityListener
import tm.relying.party.tool.util.AuthSuccessSecurityListener
import tm.relying.party.tool.util.TRPTSecurity

// Place your Spring DSL code here
beans = {

  userPasswordEncoderListener(UserPasswordEncoderListener)
  authFailureListener(AuthFailureSecurityListener.class)
  authSuccessListener(AuthSuccessSecurityListener.class)

    // Gives methods for using in @Secured annotation.
    trptSecurity(TRPTSecurity.class)
}
