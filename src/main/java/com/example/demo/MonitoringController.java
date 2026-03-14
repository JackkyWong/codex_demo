package com.example.demo;

import com.example.demo.monitoring.AlertMessage;
import com.example.demo.monitoring.MetricSnapshot;
import com.example.demo.monitoring.MonitoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/metrics")
    public MetricSnapshot metrics() {
        return monitoringService.getCurrentMetrics();
    }

    @GetMapping("/alerts")
    public List<AlertMessage> alerts() {
        return monitoringService.getRecentAlerts();
    }
}
