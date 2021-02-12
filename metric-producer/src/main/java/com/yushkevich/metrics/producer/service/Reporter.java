package com.yushkevich.metrics.producer.service;

import com.yushkevich.metrics.commons.message.Metric;
import com.yushkevich.metrics.producer.config.ReporterProperties;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public Stream<Metric> getMetrics() {
        var memory = hal.getMemory();
        var freeMemoryPct = Metric.newBuilder()
                .setDescription("Free amount of memory in percents")
                .setName("free_memory_pct")
                .setValue((double) memory.getAvailable() * 100 / memory.getTotal())
                .setCreatedAt(LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())
                .setEnv(reporterProperties.getEnv())
                .build();

        double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = cpu.getSystemCpuLoadTicks();
        var cpuLoadPct = Metric.newBuilder()
                .setDescription("CPU load")
                .setName("cpu_load_pct")
                .setValue(cpuLoad)
                .setCreatedAt(LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())
                .setEnv(reporterProperties.getEnv())
                .build();

        var freeDiscSpacePct = os.getFileSystem().getFileStores().stream()
                .map(fs -> Metric.newBuilder()
                        .setDescription(String.format("Free amount of disc space on volume (%s) in percents", fs.getName()))
                        .setName("free_disc_space_pct")
                        .setValue((double) fs.getUsableSpace() * 100 / fs.getTotalSpace())
                        .setCreatedAt(LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())
                        .setEnv(reporterProperties.getEnv())
                        .build());

        return Stream.concat(Stream.of(freeMemoryPct, cpuLoadPct), freeDiscSpacePct);
    }
}
