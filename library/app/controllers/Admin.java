package controllers;

import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Admin extends Controller {
    public static void index() {
        render();
    }

}
