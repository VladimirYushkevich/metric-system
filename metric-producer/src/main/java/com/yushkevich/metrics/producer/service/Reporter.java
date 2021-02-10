package com.yushkevich.metrics.producer.service;

import com.yushkevich.metrics.commons.message.OSMetric;
import com.yushkevich.metrics.producer.config.ReporterProperties;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Reporter {

    private final ReporterProperties reporterProperties;
    private final SystemInfo si;
    private final HardwareAbstractionLayer hal;
    private final CentralProcessor cpu;
    private final OperatingSystem os;

    private long[] prevTicks;

    public Reporter(ReporterProperties reporterProperties) {
        this.reporterProperties = reporterProperties;
        this.si = new SystemInfo();
        this.hal = si.getHardware();
        this.cpu = hal.getProcessor();
        this.os = si.getOperatingSystem();
        this.prevTicks = new long[CentralProcessor.TickType.values().length];
    }

    public Stream<OSMetric> getMetrics() {
        var memory = hal.getMemory();
        var freeMemoryPct = OSMetric.newBuilder()
                .withDescription("Free amount of memory in percents")
                .withName("free_memory_pct")
                .withValue((double) memory.getAvailable() * 100 / memory.getTotal())
                .withCreatedAt(LocalDateTime.now())
                .withEnv(reporterProperties.getEnv())
                .build();

        double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = cpu.getSystemCpuLoadTicks();
        var cpuLoadPct = OSMetric.newBuilder()
                .withDescription("CPU load")
                .withName("cpu_load_pct")
                .withValue(cpuLoad)
                .withCreatedAt(LocalDateTime.now())
                .withEnv(reporterProperties.getEnv())
                .build();

        var freeDiscSpacePct = os.getFileSystem().getFileStores().stream()
                .map(fs -> OSMetric.newBuilder()
                        .withDescription(String.format("Free amount of disc space on volume (%s) in percents", fs.getName()))
                        .withName("free_disc_space_pct")
                        .withValue((double) fs.getUsableSpace() * 100 / fs.getTotalSpace())
                        .withCreatedAt(LocalDateTime.now())
                        .withEnv(reporterProperties.getEnv())
                        .build());

        return Stream.concat(Stream.of(freeMemoryPct, cpuLoadPct), freeDiscSpacePct);
    }
}
