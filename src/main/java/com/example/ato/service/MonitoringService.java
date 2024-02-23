package com.example.ato.service;

import com.example.ato.model.HostInfo;
import com.example.ato.model.Monitoring;
import com.example.ato.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private MonitoringRepository monitoringRepository;

    public List<Monitoring> getMonitoringLog() {
        List<Monitoring> monitoringLog = monitoringRepository.selectMonitoringLog();
        return monitoringLog;
    }
}
