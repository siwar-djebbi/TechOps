package tn.esprit.se.pispring.events;

import org.springframework.context.ApplicationEvent;
import tn.esprit.se.pispring.entities.User;

public class NewUserAddedEvent extends ApplicationEvent {

    private String applicationUrl;

    private String defaultPassword;

    private User user;
    public NewUserAddedEvent(String applicationUrl, User user, String defaultPassword) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
        this.defaultPassword = defaultPassword;


    }
}
