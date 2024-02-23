package com.example.ato.service;

import com.example.ato.model.HostInfo;
import com.example.ato.repository.HostRepository;
import com.example.ato.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
@Service
public class HostMonitoringService {
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private MonitoringRepository monitoringRepository;
    public HostInfo getCurrentHostInfo(String id, String ip){

        HostInfo hosts = new HostInfo();
        hosts.setIp(ip);

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (inetAddress.isReachable(1000)) { //timeout 2초
                hosts.setReachable(true);
                hostRepository.updateReachableInfo(hosts);

            } else {
                hosts.setReachable(false);
                hostRepository.updateReachableInfo(hosts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HostInfo currentHostInfo = hostRepository.selectCurrentHostInfo(hosts.getIp());

        monitoringRepository.write(id,"Host : "+ip+" 현재 상태 조회 / Reachable : "+ hosts.isReachable(),"SUCCESS");
        return currentHostInfo;
    }

    public List<HostInfo> getAllHostMonitoringInfo(String id){
        System.out.println("getAllHostMonitoringInfo");
        List<String> ipList = hostRepository.selectAllIp();
        HostInfo hosts = new HostInfo();

        for(String ip : ipList){
            hosts.setIp(ip);
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(ip);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (inetAddress.isReachable(10)) {
                    hosts.setReachable(true);
                    hostRepository.updateReachableInfo(hosts);

                } else {
                    hosts.setReachable(false);
                    hostRepository.updateReachableInfo(hosts);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        monitoringRepository.write(id," 전체 Host 현재 상태 조회 ","SUCCESS");
        return hostRepository.selectAllHostMonitoringInfo();
    }
}
