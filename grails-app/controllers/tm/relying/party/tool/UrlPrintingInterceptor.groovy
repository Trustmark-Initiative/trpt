package tm.relying.party.tool


class UrlPrintingInterceptor {

    def springSecurityService

    UrlPrintingInterceptor(){
        matchAll()
    }

    boolean before(){
        try {
            if (controllerName != 'assets') {
                log.info("URL[@|cyan ${controllerName}|@:@|green ${actionName}|@${params.id ? ':' + params.id : ''}] [user:@|yellow ${springSecurityService.currentUser ?: 'anonymous'}|@]")
            }
        }catch(Throwable t){}
        return true;
    }

    boolean after() {
        return true;
    }

    void afterView() {
        // no-op
    }

}
