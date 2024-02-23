package com.example.ato.service;

import com.example.ato.exception.CustomException;
import com.example.ato.exception.ErrorCode;
import com.example.ato.repository.HostRepository;
import com.example.ato.model.HostInfo;
import com.example.ato.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    public List<HostInfo> getHosts(String id) {
            List<HostInfo> hosts = hostRepository.selectHosts();
            monitoringRepository.write(id," Host 정보 조회 ","SUCCESS");
        return hosts;
    }

    public HostInfo getHost(int num) {
        HostInfo host = hostRepository.selectHost(num);
        return host;
    }

    public void addHost(String id, String name, String ip) {

        int hostCount = hostCount();

        if(isHostExist(ip,name)) {
            monitoringRepository.write(id,"이미 입력된 호스트 정보입니다. name : " + name + "ip : " +ip,"FAIL");
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        if(hostCount<100){
            HostInfo hosts = new HostInfo();
            hosts.setName(name);
            hosts.setIp(ip);

//        InetAddress inetAddress = null;
//        try {
//            inetAddress = InetAddress.getByName(ip);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            if (inetAddress.isReachable(2000)) { //timeout 2초
//                System.out.println("Reachable"); //응답한 경우
//                hosts.setReachable(true);
//
//            } else {
//                System.out.println("Unreachable"); //응답이 안된 경우
//                hosts.setReachable(false);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            monitoringRepository.write(id," Host 등록 Host명:" + name + "ip : " + ip,"SUCCESS");

            hostRepository.addHost(hosts);
        }
        else{
            monitoringRepository.write(id," Host 등록 수 제한으로 인한 실패 Host명:" + name + "ip : " + ip,"FAIL");
            throw new CustomException(ErrorCode.LIMIT_HOST_COUNT);
        }

    }

    public void updateHost(String name, String ip,String id, int num){

        HostInfo hosts = new HostInfo();
        hosts.setName(name);
        hosts.setIp(ip);
        hosts.setNum(num);

        HostInfo beforeHostInfo = hostRepository.selectHost(num);

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (inetAddress.isReachable(2000)) {
                hosts.setReachable(true);

            } else {
                hosts.setReachable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        hostRepository.updateHost(hosts);
        monitoringRepository.write(id," Host 정보 변경 기존 Host명:" + beforeHostInfo.getName()  +
                                                            " 변경 Host명 : " + name +
                                                            " 기존 ip : " + beforeHostInfo.getIp() +
                                                            "변경 ip : " + ip,"SUCCESS");
    }

    public void deleteHost(String id,int num){
        HostInfo beforeHostInfo = hostRepository.selectHost(num);
        hostRepository.deleteHost(num);
        monitoringRepository.write(id," Host 정보 삭제 Host명:" + beforeHostInfo.getName() + "ip : " + beforeHostInfo.getIp() ,"SUCCESS");
    }

    public int hostCount(){
        return hostRepository.registerHostCount();
    }

    private boolean isHostExist(String ip, String name) {
        HostInfo hostInfo = new HostInfo();
        hostInfo.setIp(ip);
        hostInfo.setName(name);

        return hostRepository.selectRegistedHost(hostInfo) != null;
    }

}
