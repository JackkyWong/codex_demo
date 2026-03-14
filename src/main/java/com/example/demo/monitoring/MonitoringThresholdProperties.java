package com.example.demo.monitoring;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "monitor.threshold")
public class MonitoringThresholdProperties {

    @Min(1)
    @Max(100)
    private double cpu = 80;

    @Min(1)
    @Max(100)
    private double memory = 85;

    @Min(1)
    @Max(100)
    private double disk = 90;

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public double getDisk() {
        return disk;
    }

    public void setDisk(double disk) {
        this.disk = disk;
    }
}
