package com.absolutegalaber.buildz.multitenancy.config

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class MultitenancyEnabledCondition implements Condition {

    @Override
    boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("multitenancy.enabled", Boolean.class, false)
    }
}
