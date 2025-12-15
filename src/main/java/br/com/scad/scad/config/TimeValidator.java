package br.com.scad.scad.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeValidator {

    private static final Logger log = LoggerFactory.getLogger(TimeValidator.class);

    @PostConstruct
    public void validateServerTime() {
        ZonedDateTime serverTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS OOOO");

        log.info("============================================================");
        log.info("VALIDANDO HORÁRIO DO SERVIDOR TOMCAT");
        log.info("Horário Completo do Servidor: {}", serverTime.format(formatter));
        log.info("Fuso Horário Padrão da JVM: {}", serverTime.getZone());
        log.info("============================================================");
    }
}
    