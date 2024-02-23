package com.example.ato.controller;

import com.example.ato.model.HostInfo;
import com.example.ato.service.HostService;
import com.example.ato.util.JsonControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Admin
@RestController
@RequestMapping("/hostManager")
public class HostController { // 관리자용 호스트 관리 api

    private JsonControllerUtil util;
    private HostService hostService;

    public HostController(JsonControllerUtil util, HostService hostService) {
        this.util = util;
        this.hostService = hostService;
    }

    //조회
    @RequestMapping(value = "/hostsInfo", method = RequestMethod.GET)
    public ResponseEntity<String> hostsInfo(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id) throws Exception {
        List<HostInfo> hostList = hostService.getHosts(id);
        return util.getJsonResponseEntity(hostList);
    }

    //추가
    @RequestMapping(value = "/addHost", method = RequestMethod.POST)
    public ResponseEntity<String> addHost(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "ip", required = false) String ip) {
        hostService.addHost(id, name, ip);
        return util.getResponseEntity();

    }
//수정
    @RequestMapping(value = "/updateHost", method = RequestMethod.PUT)
    public ResponseEntity<String> updateHost(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "ip", required = false) String ip
            , @RequestParam(value = "num", required = false) int num) {
        //TODO: process PUT request
        hostService.updateHost(name, ip, id, num);
        return util.getResponseEntity();
    }
//삭제
    @RequestMapping(value = "/deleteHost", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteHost(HttpServletRequest httpRequest
            , @RequestParam(value = "id", required = false) String id
            , @RequestParam(value = "num", required = false) int num) {
        hostService.deleteHost(id, num);
        return util.getResponseEntity();
    }
}
