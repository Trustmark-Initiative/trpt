package edu.gatech.gtri.trustmark.trpt.domain


import static org.gtri.fj.data.List.iterableList
import static org.gtri.fj.data.Option.fromNull

class Server {

    String url

    long idHelper() { id }

    void deleteHelper() {

        save(failOnError: true);
    }

    Server saveHelper() {

        save(failOnError: true);
    }

    void deleteAndFlushHelper() {

        delete(flush: true, failOnError: true)
    }

    Server saveAndFlushHelper() {

        save(flush: true, failOnError: true)
    }

    static final org.gtri.fj.data.List<Server> findAllHelper() {

        fromNull(findAll())
                .map({ list -> iterableList(list) })
                .orSome(org.gtri.fj.data.List.<Server> nil());
    }
}
