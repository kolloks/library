package controllers;

import models.User;
import play.data.validation.Valid;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        return User.connect(username, password) != null;
    }

    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byEmail", connected()).<User>first().isAdmin;
        }
        if("user".equals(profile)) {
            return User.find("byEmail", connected()).<User>first()!=null;
        }
        return false;
    }

    public static void signup() {
        render();
    }

    public static void signupPost(@Valid User user) {
        if (validation.hasErrors()) {
            render("@signup", user);
        }
        user.save();
        redirect("Application.index");
    }

    static void onDisconnected() {
        Application.index();
    }
    static void onAuthenticated() {
        Application.index();
    }
}
