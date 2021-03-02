package gps.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpsBeans {

    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }
}
