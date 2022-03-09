package com.novardis.education.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "time")
public class TimeProperties {
    private Long databaseTimerPeriod;
    private Long attemptRetryPeriod;
}
