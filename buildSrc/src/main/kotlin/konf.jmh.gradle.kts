plugins {
    id("me.champeau.jmh")
    id("org.jetbrains.kotlin.plugin.allopen")
}

allOpen {
    annotation("org.openjdk.jmh.annotations.BenchmarkMode")
    annotation("org.openjdk.jmh.annotations.State")
}

jmh {
    jmhVersion = libs.version("jmh")
    iterations = 10
    batchSize = 1
    fork = 1
    timeOnIteration = "1s"
    threads = 4
    verbosity = "NORMAL"
    warmup = "1s"
    warmupBatchSize = 1
    warmupIterations = 10
    zip64 = false
}
