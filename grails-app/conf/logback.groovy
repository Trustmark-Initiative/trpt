import java.nio.charset.StandardCharsets

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = StandardCharsets.UTF_8

        pattern = '%d{yyyy-MM-dd HH:mm:ss} | %-5p | %m \\(%logger{20}\\)%n'
    }
}
root(ERROR, ['STDOUT'])

logger('edu.gatech.gtri.trustmark', INFO)
logger('edu.gatech.gtri.trustmark', INFO)
//logger("org.hibernate.type.descriptor.sql.BasicBinder", TRACE, ["STDOUT"], false)
