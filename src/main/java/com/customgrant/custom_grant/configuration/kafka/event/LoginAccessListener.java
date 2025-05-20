package com.customgrant.custom_grant.configuration.kafka.event;

import com.customgrant.custom_grant.dtos.AccessLoginDTO;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginAccessListener {

    private final KafkaTemplate<String, AccessLoginDTO> kafkaTemplate;


    public LoginAccessListener(final KafkaTemplate<String, AccessLoginDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onLoginAccess(final LoginAccessEvent event) {
        final AccessLoginDTO accessLogin = event.getLoginInfo();
        kafkaTemplate.send("accessLogin", accessLogin.username(), accessLogin);
        System.out.println("✅ Mensagem enviada para Kafka: " + event.getLoginInfo());
    }
}
