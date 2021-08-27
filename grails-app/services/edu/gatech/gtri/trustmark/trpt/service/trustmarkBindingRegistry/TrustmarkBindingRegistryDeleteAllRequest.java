package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import java.util.List;

public final class TrustmarkBindingRegistryDeleteAllRequest {

    private List<Long> idList;

    public TrustmarkBindingRegistryDeleteAllRequest() {
    }

    public TrustmarkBindingRegistryDeleteAllRequest(
            final List<Long> idList) {

        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(final List<Long> idList) {
        this.idList = idList;
    }
}
