package com.zhixing.employlib.model.resume;

import java.io.Serializable;

public class CompanyEntity implements Serializable {

    public String company;
    public String job;
    public String StartTime;
    public String EndTime;
    public String desc;
    public String id;

    public CompanyEntity(String company, String job, String startTime, String endTime, String desc, String id) {
        this.company = company;
        this.job = job;
        StartTime = startTime;
        EndTime = endTime;
        this.desc = desc;
        this.id = id;
    }
}
