package model;

import enums.Feature;
import enums.HexVisibility;
import enums.Improvement;
import enums.Terrain;

import javax.sql.XAConnection;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Game {
    private static Game game = null;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;

    private int xMap = 20;
    private int yMap = 20;
    public ArrayList<ArrayList<Hex>> map;
 /*
    structure of map:
              y=0      y=1       y=2      y=3
    x=0      [0][0]   [0][1]   [0][2]   [0][3]

    x=1          [1][0]   [1][1]   [1][2]   [0][3]

    x=2      [2][0]   [2][1]   [2][2]   [0][3]

    x=3          [3][0]   [3][1]   [3][2]   [0][3]
*/

    private Game() {
        turn = 0;
    }


    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public void startNewGame(ArrayList<User> users) {
        for (User user : users) {
            civilizations.add(new Civilization(user));
        }
        createMap();
    }


    public Civilization getPlayingCivilization() {
        return civilizations.get(turn % civilizations.size());
    }

    public int getRows() {
        return xMap;
    }

    public int getColumns() {
        return yMap;
    }

    private void createMap() {
        map = new ArrayList<>();
        for (int i = 0; i < xMap; i++) {
            ArrayList<Hex> row = new ArrayList<>();
            for (int j = 0; j < yMap; j++) {
                Hex hex = createRandomHex(i, j);
                row.add(hex);
            }
            map.add(row);
        }

        // TODO: 4/24/2022 flat and river oisess resorces
    }

    private Hex createRandomHex(int x, int y) {
        Random random = new Random();

        int randomNumber = random.nextInt(100);
        if (inPoles(x)) {
            if (randomNumber < 80)
                return new Hex(Terrain.SNOWLAND, Feature.ICE, null, false, x, y);
            else
                return new Hex(Terrain.SNOWLAND, null, null, false, x, y);
        }
        if (inAroundOcean(x, y)) {
            if (randomNumber < 90)
                return new Hex(Terrain.OCEAN, null, null, false, x, y);
            else if (randomNumber < 95)
                return new Hex(Terrain.MOUNTAIN, null, null, false, x, y);
            else
                return new Hex(Terrain.PLAIN, Feature.DENSE_FOREST, null, false, x, y);
        }
        if (percentInMiddleX(x) > 80) { // tropical
            if (randomNumber < 33)
                return new Hex(Terrain.PLAIN, randomFeatureTropical(random), null, false, x, y);
            else if (randomNumber < 66)
                return new Hex(Terrain.HILL, randomFeatureTropical(random), null, false, x, y);
            else
                return new Hex(Terrain.GRASSLAND, randomFeatureTropical(random), null, false, x, y);
        }
        if (percentInMiddleX(x) < 20) { // near poles
            if (randomNumber < 33)
                return new Hex(Terrain.PLAIN, randomFeatureNearPole(random), null, false, x, y);
            else if (randomNumber < 60)
                return new Hex(Terrain.TUNDRA, randomFeatureNearPole(random), null, false, x, y);
            else if (randomNumber < 70)
                return new Hex(Terrain.MOUNTAIN, randomFeatureNearPole(random), null, false, x, y);
            else if (randomNumber < 80)
                return new Hex(Terrain.SNOWLAND, randomFeatureNearPole(random), null, false, x, y);
            else
                return new Hex(Terrain.HILL, randomFeatureNearPole(random), null, false, x, y);
        }
        //between pole and tropical
        if (randomNumber < 30)
            return new Hex(Terrain.DESERT, randomFeatureInBetween(random), null, false, x, y);
        else if (randomNumber < 60)
            return new Hex(Terrain.GRASSLAND, randomFeatureInBetween(random), null, false, x, y);
        else if (randomNumber < 67)
            return new Hex(Terrain.MOUNTAIN, randomFeatureInBetween(random), null, false, x, y);
        else if (randomNumber < 80)
            return new Hex(Terrain.HILL, randomFeatureInBetween(random), null, false, x, y);
        else
            return new Hex(Terrain.PLAIN, randomFeatureInBetween(random), null, false, x, y);
    }

    private Feature randomFeatureTropical(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 25)
            return null;
        else if (randomNumber < 40)
            return Feature.SWAMP;
        else if (randomNumber < 55)
            return Feature.DENSE_FOREST;
        else
            return Feature.JUNGLE;
    }

    private Feature randomFeatureNearPole(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 35)
            return null;
        else if (randomNumber < 50)
            return Feature.SWAMP;
        else if (randomNumber < 68)
            return Feature.DENSE_FOREST;
        else if (randomNumber < 95)
            return Feature.JUNGLE;
        else
            return Feature.ICE;
    }

    private Feature randomFeatureInBetween(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 70)//oasis flat
            return null;
        else if (randomNumber < 85)
            return Feature.JUNGLE;
        else if (randomNumber < 90)
            return Feature.DENSE_FOREST;
        else
            return Feature.SWAMP;
    }


    private boolean inPoles(int x) {
        if (x < 2 || x > xMap - 2)
            return true;
        return false;
    }

    private boolean inAroundOcean(int x, int y) {
        if (percentInMiddleX(x) * 1.5 + percentInMiddleY(y) < 20)
            return true;
        return false;
    }

    private int percentInMiddleX(int x) {
        int differenceFromMiddle = abs(xMap / 2 - x);
        return 100 - differenceFromMiddle * 100 / (xMap / 2);
    }

    private int percentInMiddleY(int y) {
        int differenceFromMiddle = abs(yMap / 2 - y);
        return 100 - differenceFromMiddle * 100 / (yMap / 2);
    }


}
