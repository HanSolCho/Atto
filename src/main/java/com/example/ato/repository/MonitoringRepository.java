package com.example.ato.repository;

import com.example.ato.model.Monitoring;
import com.example.ato.repository.mapper.MonitoringMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonitoringRepository {
    private MonitoringMapper mapper;
    public MonitoringRepository(MonitoringMapper mapper){
        this.mapper = mapper;
    }
    public void write(String id, String content, String result){
        Monitoring monitoring = new Monitoring();
        monitoring.setId(id);
        monitoring.setContent(content);
        monitoring.setResult(result);
        mapper.write(monitoring);
    };

    public List<Monitoring> selectMonitoringLog(){
        return mapper.selectMonitoringLog();
    }
}
