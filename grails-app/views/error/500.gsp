<!doctype html>
<html>
    <head>

        <title>${grailsApplication.config.getProperty('server.title')}: Error</title>
        <meta name="layout" content="main">
    </head>

    <body>
        <div class="container pt-4">
            <g:if env="development">
                <g:if test="${Throwable.isInstance(exception)}">
                    <g:renderException exception="${exception}"/>
                </g:if>
                <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
                    <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}"/>
                </g:elseif>
                <g:else>
                    <h2>Error</h2>

                    <div class="pt-2">
                        An error has occurred.
                    </div>

                    <div class="pt-2">
                        Path: ${path == null ? "(none)" : path}
                    </div>

                    <div class="pt-2">
                        Message: ${message == null ? "(none)" : message}
                    </div>

                    <div class="pt-2">
                        <code>
                            ${exception}
                        </code>
                    </div>
                </g:else>
            </g:if>
            <g:else>
                <h2>Error</h2>

                <div class="pt-2">
                    An error has occurred.
                </div>
            </g:else>
        </div>
    </body>
</html>
