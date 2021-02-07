package com.yushkevich.metric.producer;

import com.yushkevich.metrics.commons.message.OSMetric;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Reporter {

    private final SystemInfo si;
    private final HardwareAbstractionLayer hal;
    private final CentralProcessor cpu;
    private final OperatingSystem os;

    private long[] prevTicks;

    public Reporter() {
        si = new SystemInfo();
        hal = si.getHardware();
        cpu = hal.getProcessor();
        os = si.getOperatingSystem();
        prevTicks = new long[CentralProcessor.TickType.values().length];
    }

    public Stream<OSMetric> getMetrics() {
        var memory = hal.getMemory();
        var freeMemoryPct = OSMetric.newBuilder()
                .withDescription("Free amount of memory in percents")
                .withName("free_memory_pct")
                .withValue((double) memory.getAvailable() * 100 / memory.getTotal())
                .withTime(LocalDateTime.now())
                .build();

        double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = cpu.getSystemCpuLoadTicks();
        var cpuLoadPct = OSMetric.newBuilder()
                .withDescription("CPU load")
                .withName("cpu_load_pct")
                .withValue(cpuLoad)
                .withTime(LocalDateTime.now())
                .build();

        var freeDiscSpacePct = os.getFileSystem().getFileStores().stream()
                .map(fs -> OSMetric.newBuilder()
                        .withDescription(String.format("Free amount of disc space on volume (%s) in percents", fs.getName()))
                        .withName("free_disc_space_pct")
                        .withValue((double) fs.getUsableSpace() * 100 / fs.getTotalSpace())
                        .withTime(LocalDateTime.now())
                        .build());

//        return Stream.concat(Stream.of(freeMemoryPct, cpuLoadPct), freeDiscSpacePct);
        return Stream.of(cpuLoadPct);
    }
}
