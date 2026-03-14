package com.example.demo.monitoring;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;

@Service
public class SystemMetricsService {

    public MetricSnapshot collect() {
        return new MetricSnapshot(readCpuUsage(), readMemoryUsage(), readDiskUsage());
    }

    private double readCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        if (osBean == null) {
            return 0D;
        }

        double cpuLoad = osBean.getCpuLoad();
        if (cpuLoad < 0) {
            return 0D;
        }
        return cpuLoad * 100;
    }

    private double readMemoryUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        if (osBean == null) {
            return 0D;
        }

        long total = osBean.getTotalMemorySize();
        long free = osBean.getFreeMemorySize();
        if (total <= 0) {
            return 0D;
        }
        return (double) (total - free) / total * 100;
    }

    private double readDiskUsage() {
        long totalSpace = 0;
        long usableSpace = 0;

        for (FileStore fileStore : FileSystems.getDefault().getFileStores()) {
            try {
                totalSpace += fileStore.getTotalSpace();
                usableSpace += fileStore.getUsableSpace();
            } catch (Exception ignored) {
                // Ignore inaccessible file stores.
            }
        }

        if (totalSpace <= 0) {
            return 0D;
        }

        return (double) (totalSpace - usableSpace) / totalSpace * 100;
    }
}
