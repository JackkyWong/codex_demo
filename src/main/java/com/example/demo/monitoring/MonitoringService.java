package com.example.demo.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class MonitoringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringService.class);
    private static final int MAX_ALERT_HISTORY = 100;

    private final SystemMetricsService metricsService;
    private final MonitoringThresholdProperties thresholdProperties;
    private final Queue<AlertMessage> recentAlerts = new ArrayDeque<>();

    public MonitoringService(SystemMetricsService metricsService,
                             MonitoringThresholdProperties thresholdProperties) {
        this.metricsService = metricsService;
        this.thresholdProperties = thresholdProperties;
    }

    public MetricSnapshot getCurrentMetrics() {
        return metricsService.collect();
    }

    public synchronized List<AlertMessage> getRecentAlerts() {
        return new ArrayList<>(recentAlerts);
    }

    @Scheduled(fixedDelayString = "${monitor.poll-interval-ms:10000}")
    public void checkThresholds() {
        MetricSnapshot snapshot = metricsService.collect();
        evaluate(MetricType.CPU, snapshot.cpuUsagePercent(), thresholdProperties.getCpu());
        evaluate(MetricType.MEMORY, snapshot.memoryUsagePercent(), thresholdProperties.getMemory());
        evaluate(MetricType.DISK, snapshot.diskUsagePercent(), thresholdProperties.getDisk());
    }

    private void evaluate(MetricType type, double usage, double threshold) {
        if (usage < threshold) {
            return;
        }

        String message = "%s usage %.2f%% reached threshold %.2f%%".formatted(type.name(), usage, threshold);
        AlertMessage alert = new AlertMessage(type, usage, threshold, message, Instant.now());
        addAlert(alert);
        LOGGER.warn(message);
    }

    private synchronized void addAlert(AlertMessage alert) {
        if (recentAlerts.size() >= MAX_ALERT_HISTORY) {
            recentAlerts.poll();
        }
        recentAlerts.offer(alert);
    }
}
