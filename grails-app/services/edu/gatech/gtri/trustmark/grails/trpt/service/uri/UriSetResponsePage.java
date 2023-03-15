package edu.gatech.gtri.trustmark.grails.trpt.service.uri;

import java.util.List;

public class UriSetResponsePage {

    private final long count;
    private final long offset;
    private final long max;
    private final List<UriResponse> list;

    public UriSetResponsePage(
            final long count,
            final long offset,
            final long max,
            final List<UriResponse> list) {

        this.count = count;
        this.offset = offset;
        this.max = max;
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public long getOffset() {
        return offset;
    }

    public long getMax() {
        return max;
    }

    public List<UriResponse> getList() {
        return list;
    }
}
