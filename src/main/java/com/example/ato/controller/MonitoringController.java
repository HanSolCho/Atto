package com.example.ato.controller;

import com.example.ato.model.HostInfo;
import com.example.ato.model.Monitoring;
import com.example.ato.service.HostMonitoringService;
import com.example.ato.service.MonitoringService;
import com.example.ato.util.JsonControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonitoringController { //감사기록 확인용 api

    private JsonControllerUtil util;
    private MonitoringService monitoringService;

    public MonitoringController(JsonControllerUtil util, MonitoringService monitoringService){
        this.util = util;
        this.monitoringService = monitoringService;
    }
    @RequestMapping(value = "/logMonitoring", method = RequestMethod.GET)
    public ResponseEntity<String> currentHostInfo(HttpServletRequest httpRequest
            , @RequestParam(value="id", required = false) String id) {
        List<Monitoring> monitoringLog = monitoringService.getMonitoringLog();
        return util.getJsonResponseEntity(monitoringLog);
    }
}
