package edu.gatech.gtri.trustmark.trpt.service.user;

import java.util.List;

public class UserDeleteAllRequest {

    private List<Long> idList;

    public UserDeleteAllRequest() {
    }

    public UserDeleteAllRequest(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
