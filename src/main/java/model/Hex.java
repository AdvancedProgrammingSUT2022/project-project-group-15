package model;

import enums.*;

import java.util.HashMap;

public class Hex {
    private Civilization owner;
    private Terrain terrain;
    private Feature feature;
    private Resource resource;
    private Improvement improvement;
    private boolean hasDestroyedImprovement;
    private int percentOfBuildingImprovement;
    private boolean isAnyCitizenWorking;
    private int movementPrice;
    private boolean hasRiver;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private HexVisibility hexVisibility=HexVisibility.FOG_OF_WAR;
    private HashMap<Character, Integer> coordinatesInArray = new HashMap<>();
    private HashMap<Character, Integer> coordinatesInMap = new HashMap<>();
    private MilitaryUnit militaryUnit;
    private CivilUnit civilUnit;

    public Hex(Terrain terrain, Feature feature, Resource resource, boolean hasRiver, int x, int y) {
        this.terrain = terrain;
        this.feature = feature;
        this.resource = resource;
        this.hasRiver = hasRiver;
        this.coordinatesInArray.put('x', x);
        this.coordinatesInArray.put('y', y);
        this.coordinatesInMap.put('x', x);
        this.coordinatesInMap.put('y', y*2 + x%2);
        this.movementPrice = calculateMovementPrice();
    }

    private int calculateMovementPrice() {
        // TODO: 4/24/2022
        return 0;
    }

    public HexVisibility getHexVisibility() {
        return hexVisibility;
    }

    public void setHexVisibility(HexVisibility hexVisibility) {
        this.hexVisibility = hexVisibility;
    }

    public MilitaryUnit getMilitaryUnit() {
        return militaryUnit;
    }

    public void setMilitaryUnit(MilitaryUnit militaryUnit) {
        this.militaryUnit = militaryUnit;
    }

    public CivilUnit getCivilUnit() {
        return civilUnit;
    }

    public void setCivilUnit(CivilUnit civilUnit) {
        this.civilUnit = civilUnit;
    }

    public HashMap<Character, Integer> getCoordinatesInArray() {
        return coordinatesInArray;
    }

    public HashMap<Character, Integer> getCoordinatesInMap() {
        return coordinatesInMap;
    }

    public int getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(int movementPrice) {
        this.movementPrice = movementPrice;
    }

    public boolean doesHaveRiver() {
        return hasRiver;
    }

    public void setHasRiver(boolean hasRiver) {
        this.hasRiver = hasRiver;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
