package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Server
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['by', 'note'])
class IReservation {
    String by
    String note

    static IReservation by(Server.Reservation reservation) {
        if (!reservation) {
            return null
        }

        new IReservation(by: reservation.by, note: reservation.note)
    }
}
