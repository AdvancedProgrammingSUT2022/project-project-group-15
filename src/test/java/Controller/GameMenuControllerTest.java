package Controller;

import controller.GameMenuController;
import junit.framework.TestCase;
import model.Game;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import view.GameMenu;

import java.util.ArrayList;

public class GameMenuControllerTest {


    @Test
    public void seeMapWithSomeChanges() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","","0"));
        users.add(new User("","","1"));
        users.add(new User("","","2"));
        users.add(new User("","","3"));
        Game.startNewGame(users);
        GameMenu gameMenu =new GameMenu();
        gameMenu.run();

    }

}