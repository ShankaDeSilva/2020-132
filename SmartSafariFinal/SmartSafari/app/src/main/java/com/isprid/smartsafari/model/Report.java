package com.isprid.smartsafari.model;

public class Report {
    String cid;
    int reportCount;

    public Report() {
    }

    public Report(String cid, int reportCount) {
        this.cid = cid;
        this.reportCount = reportCount;
    }

    public String getCid() {
        return cid;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
}
