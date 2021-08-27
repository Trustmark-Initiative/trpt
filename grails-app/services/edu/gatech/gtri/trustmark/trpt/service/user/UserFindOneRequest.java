package edu.gatech.gtri.trustmark.trpt.service.user;

public class UserFindOneRequest
{
    private long id;

    public UserFindOneRequest()
    {
    }

    public UserFindOneRequest(final long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(final long id)
    {
        this.id = id;
    }
}
