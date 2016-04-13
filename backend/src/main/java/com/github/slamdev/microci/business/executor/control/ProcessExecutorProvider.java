package com.github.slamdev.microci.business.executor.control;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zeroturnaround.exec.ProcessExecutor;

@Configuration
public class ProcessExecutorProvider {

    @Bean
    public ProcessExecutor get() {
        return new ProcessExecutor();
    }
}
