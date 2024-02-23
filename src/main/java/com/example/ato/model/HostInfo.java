package com.example.ato.model;

import lombok.Data;

import java.util.Date;

@Data
public class HostInfo {
    private String name;
    private String ip;
    private Date createDate;
    private Date updateDate;
    private boolean reachable;
    private Date lastAliveDate;
    private int num;
}
