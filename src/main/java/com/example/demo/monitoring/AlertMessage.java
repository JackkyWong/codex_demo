package com.example.demo.monitoring;

import java.time.Instant;

public record AlertMessage(MetricType type, double usagePercent, double thresholdPercent, String message, Instant timestamp) {
}
