package controller;

import enums.*;
import model.*;

import model.unit.*;

import java.util.ArrayList;

public class GameMenuController {
    Unit selectedUnit = null;


    public String changeTurn() {
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            if (unit.needsCommand()) return "some Units Need Command";
        }
        Game.getGame().nextTurn();
        Game.getGame().getSelectedCivilization().nextTurn();
        selectedUnit = null;
        return "done";
    }

    /**
     * shows the technology info and the available technologies
     *
     * @return message to be shown
     * @author Parsa
     */
    public String showTechnologyInfo() {
        String message = "";
        // current technology
        Technology technology = Game.getGame().getSelectedCivilization().getTechnologyInProgress();
        if (technology != null) {
            int remainingTurns = (int) Math.ceil((technology.cost - Game.getGame().getSelectedCivilization().getScienceStorage()) / (double) Game.getGame().getSelectedCivilization().getSciencePerTurn());
            message = "Current Technology : " + technology + "( " + remainingTurns + " turns remaining to achieve )\n";
        } else {
            message = "No Current Technology\n";
        }

        // available technologies
        Game.getGame().getSelectedCivilization().updateAvailableTechnologies();
        ArrayList<Technology> availableTechs = Game.getGame().getSelectedCivilization().getAvailableTechnologies();
        message += "Available Technologies :\n";
        for (int i = 0; i < availableTechs.size(); i++) {
            message += (i + 1) + ") " + availableTechs.get(i) + "\n";
        }
        return message;
    }

    public String buyNewTechnology(String technologyName) {
        Technology technology = null;
        for (Technology tech : Technology.values()) {
            if (tech.toString().equals(technologyName)) {
                technology = tech;
            }
        }

        if (technology == null) {
            return "Invalid technology name!";
        }

        if (!Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
            return "This technology is not available for you! (Open prerequisites first)";
        }

        Game.getGame().getSelectedCivilization().setTechnologyInProgress(technology);
        return technologyName + " activated! (is your technology in progress)";
    }

    public String showUnitsPanel() {
        // TODO : implement
        return null;
    }

    public String showCitiesPanel() {
        // TODO : implement
        return null;
    }

    public String showDiplomacyPanel() {
        // TODO : implement
        return null;
    }

    public String showVictoryPanel() {
        // TODO : implement
        return null;
    }

    public String showDemographicsPanel() {
        // TODO : implement
        return null;
    }

    public String showNotificationHistory() {
        // TODO : implement
        return null;
    }

    public String showMilitaryPanel() {
        // TODO : implement
        return null;
    }

    public String showEconomyPanel() {
        // TODO : implement
        return null;
    }

    public String showDealsPanel() {
        // TODO : implement
        return null;
    }

    public String selectMilitaryUnit(int x, int y) {
        // TODO : implement
        return null;
    }

    public String selectCivilUnit(int x, int y) {
        // TODO : implement
        return null;
    }

    public String selectCity(String cityName) {
        // TODO : implement
        return null;
    }

    public String selectCity(int x, int y) {
        // TODO : implement
        return null;
    }

    public String moveSelectedUnitTo(int x, int y) {
        if (selectedUnit == null)
            return "no selected unit";
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return "unit not yours";
        }
        int distance = selectedUnit.findShortestPathByDijkstra(x, y);
        if (distance > 999999) {
            return "cant go to destination (mountain or ice or sea ) or blocked by other units";
        }
        selectedUnit.doPlanedMovement();
        if (selectedUnit.getPlanedToGo() == null)
            return "move done";
        return "planed move will be done";
    }

    public String sleepSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String alertSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String fortifySelectedUnit() {
        // TODO : implement
        return null;
    }

    public String fortifySelectedUnitTillHeal() {
        // TODO : implement
        return null;
    }

    public String garrisonSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String setupRangedSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String foundCity() {
        // TODO : implement
        return null;
    }

    public String cancelSelectedUnitMission() {
        // TODO : implement
        return null;
    }

    public String wakeUpSelectedUnit() {
        // TODO : implement
        return null;
    }

    public static void buildUnit(City city, Civilization civilization, UnitName unitName) {
        User user = civilization.getUser();
        if (city.getRemainedTurns() > 0) {
            System.out.println("Not Enough turn");
            return;
        }
        if (civilization.getGoldStorage() < unitName.getCost()) {
            System.out.println("Not Enough Money");
            return;
        }
        city.setRemainedTurns(unitName.getTurn());
        city.setProgresssUnit(unitName);
        civilization.payMoney(unitName.getCost());
    }

    public static void addUnit(Civilization civilization, City city, UnitName unitName, Hex hex) {
        if (unitName.equals(unitName.SETTLER)) {
            //movementspeed va health monde
            //TODO
            SettlerUnit settlerUnit = new SettlerUnit(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'), city.getOwner(), 1, 1, unitName);
            civilization.getUnits().add(settlerUnit);
            return;
        }
        if (unitName.equals(unitName.WORKER)) {
            WorkerUnit worker = new WorkerUnit(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'), city.getOwner(), 1, 1, unitName);
            civilization.getUnits().add(worker);
            return;
        }
        if (unitName.getCombatType().equals("Archery") || unitName.getCombatType().equals("Siege") || unitName.equals(unitName.CHARIOTARCHER)) {
            RangedMilitary rangedMilitary = new RangedMilitary(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'), city.getOwner(), 1, 1, unitName);
            civilization.getUnits().add(rangedMilitary);
            return;
        }
        MeleeMilitary meleeMilitary = new MeleeMilitary(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'), city.getOwner(), 1, 1, unitName);
        civilization.getUnits().add(meleeMilitary);
    }

    public String deleteSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String attackTo(int x, int y) {
        // TODO : implement
        return null;
    }

    public String buildImprovement(String improvementName) {
        Improvement improvement = Improvement.getImprovementByName(improvementName);
        if (improvement == null) {
            return "invalid improvement";
        }

        // TODO : implement
        return null;
    }

    public String removeJungle() {
        // remove dense-forests requires Bronze-Working Technology
        // remove jungles requires Mining Technology
        // TODO : implement
        return null;
    }

    public String removeRoute() {
        // TODO : implement
        return null;
    }

    public String repair() {
        // TODO : implement
        return null;
    }

    public String showMap() {
        String[][] printMap = new String[GlobalThings.mapHeight][GlobalThings.mapWidth];
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                printMap[i][j] = GlobalThings.BLACK + '█';
            }
        }
        for (int i = 0; i < Game.getGame().getRows() * 2; i++) {
            for (int j = 0; j < Game.getGame().getColumns() / 2; j++) {
                int x = (i + 1) * GlobalThings.widthOfGrid / 2;
                int y = j * GlobalThings.lengthOfGrid * 2 + GlobalThings.lengthOfGrid;
                if (i % 2 == 1) y += GlobalThings.lengthOfGrid;
                for (int k = 0; k < GlobalThings.widthOfGrid / 2; k++) {
                    for (int l = 0; l <= 9 - k; l++) {
                        for (int z = y - l; z <= y + l; z++) {
                            if (z == y - l) printMap[x - k][z - 1] = "/";
                            if (z == y + l) printMap[x - k][z + 1] = "\\";
                            printMap[x - k][z] = GlobalThings.GREEN + '█';
                            printMap[x + k][z] = GlobalThings.GREEN + '█';
                        }
                    }
                }
                printMap[x + 1][y - 2] = GlobalThings.BLUE + "RI";
                printMap[x + 1][y - 1] = "";
                printMap[x + 1][y] = ":";
                if (Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).doesHaveRiver())
                    printMap[x + 1][y + 1] = "ys";
                else
                    printMap[x + 1][y + 1] = "no";
                printMap[x + 1][y + 2] = "";

                printMap[x][y - 2] = GlobalThings.YELLOW + "T";
                printMap[x][y - 1] = "R";
                printMap[x][y] = ":";
                printMap[x][y + 1] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(0, 1);
                printMap[x][y + 2] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(1, 2);

                printMap[x - 1][y - 2] = GlobalThings.RED + "FE";
                printMap[x - 1][y - 1] = "";
                printMap[x - 1][y] = ":";
                printMap[x - 1][y + 1] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(0, 1);
                printMap[x - 1][y + 2] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(1, 2);


                if (Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getCivilUnit() == null) {
                    replaceText(printMap, x, y, -2, "CU", "NA");
                } else if (Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getCivilUnit() instanceof WorkerUnit) {
                    replaceText(printMap, x, y, -2, "CU", "WO");
                } else {
                    replaceText(printMap, x, y, -2, "CU", "SE");
                }

                if (Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getMilitaryUnit() == null) {
                    replaceText(printMap, x, y, +2, "MU", "NA");
                } else {
                    replaceText(printMap, x, y, +2, "MU",
                            Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getMilitaryUnit().getName().toString().substring(0, 2));
                }


                printMap[x + 4][y] = "-";
                printMap[x + 4][y + 1] = "-";
                printMap[x + 4][y + 2] = "-";
                printMap[x + 4][y + 3] = "-";
                printMap[x + 4][y + 4] = "-";
                printMap[x + 4][y + 5] = "-";
                printMap[x - 4][y] = "-";
                printMap[x - 4][y + 1] = "-";
                printMap[x - 4][y + 2] = "-";
                printMap[x - 4][y + 3] = "-";
                printMap[x - 4][y + 4] = "-";
                printMap[x - 4][y + 5] = "-";
                printMap[x + 4][y - 1] = "-";
                printMap[x + 4][y - 2] = "-";
                printMap[x + 4][y - 3] = "-";
                printMap[x + 4][y - 4] = "-";
                printMap[x + 4][y - 5] = "-";
                printMap[x - 4][y - 1] = "-";
                printMap[x - 4][y - 2] = "-";
                printMap[x - 4][y - 3] = "-";
                printMap[x - 4][y - 4] = "-";
                printMap[x - 4][y - 5] = "-";
            }
        }
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                System.out.print(printMap[i][j]);
            }
            System.out.println("");
        }
        return "print map successfully";
    }

    private void replaceText(String[][] map, int x, int y, int xDiff, String firstTwo, String secondTwo) {
        map[x + xDiff][y] = ":";
        map[x + xDiff][y - 1] = "";
        map[x + xDiff][y - 2] = firstTwo;
        map[x + xDiff][y + 1] = secondTwo;
        map[x + xDiff][y + 2] = "";
    }


    public String moveMap(String directionName, int amount) {
        Direction direction = Direction.getDirectionByName(directionName);
        if (direction == null) {
            return "invalid direction";
        }

        // TODO : implement
        return null;
    }

    public String showMapOnPosition(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return "Coordinate not valid";
        return Game.getGame().getSelectedCivilization().showMapOn(x, y);
    }

    public String showMapOnCity(String cityName) {
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(cityName)) {
                    int xOfCity = city.getCoordinatesOfCenterInArray().get('x');
                    int yOfCity = city.getCoordinatesOfCenterInArray().get('y');
                    if (Game.getGame().getSelectedCivilization().getVisibilityMap().map.get(xOfCity).get(yOfCity)
                            .getHexVisibility().equals(HexVisibility.FOG_OF_WAR))
                        return "you haven't seen " + cityName;
                    else
                        return Game.getGame().getSelectedCivilization().showMapOn(xOfCity, yOfCity);
                }
            }
        }

        return "No city with this name";
    }


    // TODO : implement removing Swamp ( that requires Masonry Technology )
}
