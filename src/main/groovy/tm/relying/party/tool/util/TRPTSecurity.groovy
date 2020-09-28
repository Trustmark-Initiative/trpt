package tm.relying.party.tool.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.security.core.Authentication

import javax.servlet.http.HttpServletRequest
/**
* A class for implementing custom methods for use in TBR @Secured annotations used to secure methods.
* <br/><br/>
*/
class TRPTSecurity {

    static Logger log = LoggerFactory.getLogger(TRPTSecurity.class);

    boolean hasLock(Authentication auth, HttpServletRequest request) {
        log.info("Calling TfamSecurity.hasLock(${auth.principal.username}, ${request.getServletPath()})!")

        return true;
    }


}
