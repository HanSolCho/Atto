package com.example.ato.repository;

import com.example.ato.repository.mapper.HostMapper;
import com.example.ato.model.HostInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class HostRepository {

    private HostMapper mapper;

    public HostRepository(HostMapper mapper){
        this.mapper = mapper;
    }

    public List<HostInfo> selectHosts(){return mapper.selectHosts();}
    public HostInfo selectHost(int num){return mapper.selectHost(num);}
    public void addHost(HostInfo hosts){ mapper.addHost(hosts);}
    public void updateHost(HostInfo hosts){ mapper.updateHost(hosts);}
    public void deleteHost(int num){mapper.deleteHost(num);}
    public int registerHostCount(){return mapper.registerHostCount();}
    public HostInfo selectCurrentHostInfo(String ip){return mapper.selectCurrentHostInfo(ip);}
    public void updateReachableInfo(HostInfo hostInfo){mapper.updateReachableInfo(hostInfo);}
    public List<String> selectAllIp(){return mapper.selectAllIp();}
    public List<HostInfo> selectAllHostMonitoringInfo(){return mapper.selectAllHostMonitoringInfo();}
    public HostInfo selectRegistedHost(HostInfo hostInfo){return mapper.selectRegistedHost(hostInfo);}

}
