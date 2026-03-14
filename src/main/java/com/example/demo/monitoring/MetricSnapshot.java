package com.example.demo.monitoring;

public record MetricSnapshot(double cpuUsagePercent, double memoryUsagePercent, double diskUsagePercent) {
}
