import controller.Controller;
import controller.GameMenuController;
import model.Game;
import model.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","",""));
        users.add(new User("","",""));
        users.add(new User("","",""));
        users.add(new User("","",""));
        Game.startNewGame(users);
        System.out.println(gameMenuController.showMapOnPosition(0,0));
       // gameMenuController.showMap();
        Controller controller = new Controller();
        controller.run();
    }
}
