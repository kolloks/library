package controllers;

import models.User;
import play.mvc.Before;

public class Controller extends play.mvc.Controller {
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
}
