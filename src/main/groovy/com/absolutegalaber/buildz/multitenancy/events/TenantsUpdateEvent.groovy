package com.absolutegalaber.buildz.multitenancy.events

import org.springframework.context.ApplicationEvent

class TenantsUpdateEvent extends ApplicationEvent {

    TenantsUpdateEvent(Object source) {
        super(source)
    }

    @Override
    String toString() {
        return "Tenant Updated Event"
    }
}
