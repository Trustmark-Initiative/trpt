package edu.gatech.gtri.trustmark.grails.trpt.service.organization;

import java.util.List;

public final class OrganizationDeleteAllRequest {

    private List<Long> idList;

    public OrganizationDeleteAllRequest() {
    }

    public OrganizationDeleteAllRequest(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
