package com.example.ato.model;

import lombok.Data;
import java.util.Date;
@Data
public class Monitoring {
    private String id;
    private String content;
    private Date date;
    private String result;
}
