package com.example.ato.repository.mapper;

import com.example.ato.model.HostInfo;
import org.apache.catalina.Host;

import java.util.List;
public interface HostMapper {
    List<HostInfo> selectHosts();
    HostInfo selectHost(int num);
    void addHost(HostInfo hostInfo);
    void updateHost(HostInfo hostInfo);
    void deleteHost(int num);
    int registerHostCount();
    HostInfo selectCurrentHostInfo(String ip);
    void updateReachableInfo(HostInfo hostInfo);
    List<String> selectAllIp();
    List<HostInfo> selectAllHostMonitoringInfo();
    HostInfo selectRegistedHost(HostInfo hostInfo);
}
