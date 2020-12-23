package com.absolutegalaber.buildz.events

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class ReserveServerEvent {
    String reservedBy
    String reservationNote
}
