package com.bottle_app.event;

import com.bottle_app.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

    private final User user;

    public UserRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }

}
