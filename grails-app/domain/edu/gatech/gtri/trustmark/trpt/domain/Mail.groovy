package edu.gatech.gtri.trustmark.trpt.domain

import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class Mail {

    String host
    long port
    String username
    String password
    String author

    static constraints = {
        host nullable: true
        port nullable: true
        username nullable: true
        password nullable: true
        author nullable: true
    }

    static mapping = {
        host length: 1000
        username length: 1000
        password length: 1000
        author length: 1000
    }

    long idHelper() { id }

    void deleteHelper() {

        save(failOnError: true);
    }

    Mail saveHelper() {

        save(failOnError: true);
    }

    void deleteAndFlushHelper() {

        delete(flush: true, failOnError: true)
    }

    Mail saveAndFlushHelper() {

        save(flush: true, failOnError: true)
    }

    static final org.gtri.fj.data.List<Mail> findAllHelper() {

        fromNull(findAll())
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<Mail> nil());
    }
}
