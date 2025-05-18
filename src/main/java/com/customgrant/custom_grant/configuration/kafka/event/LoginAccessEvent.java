package com.customgrant.custom_grant.configuration.kafka.event;

import com.customgrant.custom_grant.dtos.AccessLoginDTO;
import org.springframework.context.ApplicationEvent;

public class LoginAccessEvent extends ApplicationEvent {

    private final AccessLoginDTO loginInfo;

    public LoginAccessEvent(Object source, AccessLoginDTO loginInfo) {
        super(source);
        this.loginInfo = loginInfo;
    }

    public AccessLoginDTO getLoginInfo() {
        return loginInfo;
    }
}
