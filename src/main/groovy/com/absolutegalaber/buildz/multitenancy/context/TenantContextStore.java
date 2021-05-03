package com.absolutegalaber.buildz.multitenancy.context;

public class TenantContextStore {

    private String tenantUUID;

    public String getTenantUUID() {
        return this.tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public void clear() {
        this.tenantUUID = null;
    }
}
