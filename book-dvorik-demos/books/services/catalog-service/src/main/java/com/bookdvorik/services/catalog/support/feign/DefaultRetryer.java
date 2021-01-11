package com.bookdvorik.services.catalog.support.feign;

import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;

public class DefaultRetryer extends Retryer.Default {

    public DefaultRetryer(@Value("${feign.client.config.default.period}") Long period,
                          @Value("${feign.client.config.default.maxPeriod}") Long maxPeriod,
                          @Value("${feign.client.config.default.maxAttempts}")  int maxAttempts) {
        super(period, maxPeriod, maxAttempts);
    }
}
