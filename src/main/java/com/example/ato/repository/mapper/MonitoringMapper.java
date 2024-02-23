package com.example.ato.repository.mapper;

import com.example.ato.model.HostInfo;
import com.example.ato.model.Monitoring;

import java.util.List;

public interface MonitoringMapper {
    void write(Monitoring monitoring);
    List<Monitoring> selectMonitoringLog();
}
