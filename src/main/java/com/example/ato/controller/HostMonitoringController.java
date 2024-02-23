package com.example.ato.controller;

import com.example.ato.model.HostInfo;
import com.example.ato.service.HostMonitoringService;
import com.example.ato.service.HostService;
import com.example.ato.util.JsonControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//USER,ADMIN
@RestController
@RequestMapping("/hostMonitoring")
public class HostMonitoringController { // 계정을 가진 사용자가 호스트 모니터링 api
    private JsonControllerUtil util;
    private HostMonitoringService hostMonitoringService;

    public HostMonitoringController(JsonControllerUtil util, HostMonitoringService hostMonitoringService) {
        this.util = util;
        this.hostMonitoringService = hostMonitoringService;
    }

    //호스트 현재 상태 조회
    @RequestMapping(value = "/currentHostInfo", method = RequestMethod.GET)
    public ResponseEntity<String> currentHostInfo(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id
            , @RequestParam(value = "ip", required = false) String ip) {
        HostInfo currentHostInfo = hostMonitoringService.getCurrentHostInfo(id, ip);
        return util.getJsonResponseEntity(currentHostInfo);
    }
    //호스트 모니터링
    @RequestMapping(value = "/monitoringHost", method = RequestMethod.GET)
    public ResponseEntity<String> monitoringHost(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id
            , @RequestParam(value = "ip", required = false) String ip) {
        if (ip.equals("all")) {
            //전체
            List<HostInfo> hostMonitoringInfoList = hostMonitoringService.getAllHostMonitoringInfo(id);
            return util.getJsonResponseEntity(hostMonitoringInfoList);
        } else {
            //단일
            HostInfo currentHostInfo = hostMonitoringService.getCurrentHostInfo(id, ip);
            return util.getJsonResponseEntity(currentHostInfo);
        }
    }
}
