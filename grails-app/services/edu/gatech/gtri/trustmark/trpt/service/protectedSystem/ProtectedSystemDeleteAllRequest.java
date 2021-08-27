package edu.gatech.gtri.trustmark.trpt.service.protectedSystem;

import java.util.List;

public final class ProtectedSystemDeleteAllRequest {

    private List<Long> idList;

    public ProtectedSystemDeleteAllRequest() {
    }

    public ProtectedSystemDeleteAllRequest(
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
