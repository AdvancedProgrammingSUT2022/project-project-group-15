package model;

import enums.UnitName;

public class MilitaryUnit extends Unit {
    private int meleePower;
    private boolean isAlerted;
    private boolean isFortifying;
    private boolean isFortifyingTillHealed;

    public MilitaryUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    public void attackTo(Unit unit) {

    }

    public void garrisonCity() {

    }

    public boolean enemyIsNear() {
        return true;
    }

    public boolean needsCommand() {
        return  true;
    }
}
