package com.example.demo.monitoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MonitoringServiceTest {

    private SystemMetricsService metricsService;
    private MonitoringThresholdProperties properties;
    private MonitoringService monitoringService;

    @BeforeEach
    void setUp() {
        metricsService = mock(SystemMetricsService.class);
        properties = new MonitoringThresholdProperties();
        properties.setCpu(80);
        properties.setMemory(85);
        properties.setDisk(90);
        monitoringService = new MonitoringService(metricsService, properties);
    }

    @Test
    void shouldGenerateAlertsWhenUsageExceedsThreshold() {
        when(metricsService.collect()).thenReturn(new MetricSnapshot(90, 92, 95));

        monitoringService.checkThresholds();

        assertThat(monitoringService.getRecentAlerts()).hasSize(3);
    }

    @Test
    void shouldNotGenerateAlertsWhenUsageBelowThreshold() {
        when(metricsService.collect()).thenReturn(new MetricSnapshot(10, 20, 30));

        monitoringService.checkThresholds();

        assertThat(monitoringService.getRecentAlerts()).isEmpty();
    }
}
