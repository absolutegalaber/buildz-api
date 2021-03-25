package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Server
import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@EqualsAndHashCode(includes = ['by', 'note'])
class IReservation {
    @NotNull(message = "Reservation.by mut not be empty")
    @NotEmpty(message = "Reservation.by mut not be empty")
    String by
    String note

    static IReservation by(Server.Reservation reservation) {
        if (!reservation) {
            return null
        }

        new IReservation(by: reservation.by, note: reservation.note)
    }
}
