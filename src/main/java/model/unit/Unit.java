package model.unit;

import controller.GameMenuController;
import enums.NeighborHex;
import enums.UnitName;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit {
    protected HashMap<Character, Integer> coordinatesInMap = new HashMap<>();
    protected ArrayList<Hex> PlanedToGo = new ArrayList<>();
    protected Civilization owner;
    protected int movementSpeed;
    protected int remainingMovement;
    protected int experience;
    protected int cost;
    protected UnitName name;
    protected int nowHealth;
    protected int totalHealth;
    protected boolean isSleep;
    protected int meleePower;

    public Unit(int x, int y, Civilization owner, UnitName name) {
        // TODO: 5/8/2022 use unitname for adding info 
        coordinatesInMap.put('x', x * 2 + y % 2);
        coordinatesInMap.put('y', y);
        this.owner = owner;
        this.movementSpeed = name.getMovement();
        this.remainingMovement = this.movementSpeed;
        this.meleePower = name.getCombatStrength();
        this.totalHealth = 2*meleePower;
        if (totalHealth == 0)
            totalHealth=1;
        this.name = name;
        if (this instanceof CivilUnit)
            Game.getGame().map.map.get(x).get(y).setCivilUnit((CivilUnit) this);
        else
            Game.getGame().map.map.get(x).get(y).setMilitaryUnit((MilitaryUnit) this);
        owner.getUnits().add(this);
    }

    public int getNowHealth() {
        return nowHealth;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public Civilization getOwner() {
        return owner;
    }

    public HashMap<Character, Integer> getCoordinatesInMap() {
        return coordinatesInMap;
    }

    public UnitName getName() {
        return name;
    }

    public ArrayList<Hex> getPlanedToGo() {
        return PlanedToGo;
    }

    public void doPlanedMovement() {
        Hex nextHex;
        while (remainingMovement > 0 && !PlanedToGo.isEmpty()) {
            nextHex = PlanedToGo.get(0);
            moveToHex(nextHex.getCoordinatesInArray().get('x'), nextHex.getCoordinatesInArray().get('y'));
            PlanedToGo.remove(0);
        }
        if (PlanedToGo.isEmpty())
            PlanedToGo = null;
    }

    protected void moveToHex(int x, int y) {
        if (this instanceof MilitaryUnit) {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y')).setMilitaryUnit(null);
        } else {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y')).setCivilUnit(null);
        }

        this.coordinatesInMap.replace('y', y);
        this.coordinatesInMap.replace('x', 2 * x + y % 2);
        this.remainingMovement -= Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y')).getMovementPrice();

        if (this instanceof MilitaryUnit) {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y')).setMilitaryUnit((MilitaryUnit) this);
        } else {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y')).setCivilUnit((CivilUnit) this);
        }
    }


    public int findShortestPathByDijkstra(int x, int y) {
        int numberOfRows = Game.getGame().getRows();
        int numberColumns = Game.getGame().getColumns();
        int numberOfNodes = numberColumns * numberOfRows;
        int[] parent = new int[numberOfNodes];
        int startNodeNumber = (this.coordinatesInMap.get('x') / 2) * numberColumns + this.coordinatesInMap.get('y');
        int destinationNode = x * numberColumns + y;
        // Key values used to pick minimum weight edge in cut
        int[] distance = new int[numberOfNodes];
        // To represent set of vertices included in MST
        Boolean[] mstSet = new Boolean[numberOfNodes];
        // Initialize all keys as INFINITE
        for (int i = 0; i < numberOfNodes; i++) {
            distance[i] = Integer.MAX_VALUE / 2;
            mstSet[i] = false;
        }
        // Always include first 1st vertex in MST.
        distance[startNodeNumber] = 0; // Make distance 0 so that this vertex is
        // picked as first vertex
        parent[startNodeNumber] = -1; // First node is always root of MST
        // The MST will have numberOfNodes vertices
        for (int count = 0; count < numberOfNodes - 1; count++) {
            // Pick thd minimum distance vertex from the set of vertices
            // not yet included in MST
            int u = minKey(distance, mstSet, numberOfNodes);
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
            // Update distance value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            // graph[u][v] is non zero only for adjacent vertices of m
            // mstSet[v] is false for vertices not yet included in MST
            // Update the distance only if graph[u][v] is smaller than distance[v]
            for (NeighborHex neighborHex : NeighborHex.values()) {
                updateAdjacentNode(neighborHex.xDiff, neighborHex.yDiff, distance, mstSet, u, parent);
            }

        }
        createArraylistForRoute(parent, destinationNode);
        if (distance[destinationNode] > 999999)
            PlanedToGo = null;
        return distance[destinationNode];
    }

    private void createArraylistForRoute(int[] parent, int destinationNode) {
        ArrayList<Hex> answer = new ArrayList<>();

        int x = destinationNode / (Game.getGame().getColumns());
        int y = destinationNode % (Game.getGame().getColumns());
        answer.add(0, Game.getGame().map.map.get(x).get(y));

        destinationNode = parent[destinationNode];
        int maxDepth = 100;
        while (parent[destinationNode] != -1) {
            x = destinationNode / (Game.getGame().getColumns());
            y = destinationNode % (Game.getGame().getColumns());
            answer.add(0, Game.getGame().map.map.get(x).get(y));
            destinationNode = parent[destinationNode];
            maxDepth--;
            if (maxDepth<0){
                PlanedToGo = null;
                return;
            }
        }

        PlanedToGo = answer;
    }

    private void updateAdjacentNode(int xDiff, int yDiff, int[] distance, Boolean[] mstSet, int NodeNumber, int[] parent) {
        int x = NodeNumber / (Game.getGame().getColumns());
        int y = NodeNumber % (Game.getGame().getColumns());
        x = 2 * x + y % 2 + xDiff;
        x = x / 2;
        y += yDiff;

        int destinationNodeNumber = x * (Game.getGame().getColumns()) + y;
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return;
        int moveCost = Game.getGame().map.map.get(x).get(y).getMovementPrice();
        if (Game.getGame().map.map.get(x).get(y).doesHaveRiver() && Game.getGame().map.map.
                get(NodeNumber / (Game.getGame().getColumns())).get(NodeNumber % (Game.getGame().getColumns())).doesHaveRiver())
            moveCost = this.movementSpeed;
        if (moveCost == -1)
            moveCost = Integer.MAX_VALUE / 2;

        if (mstSet[destinationNodeNumber] == false && distance[NodeNumber] + moveCost < distance[destinationNodeNumber] &&
                !hasSameUnitInHex(x, y)) {
            parent[destinationNodeNumber] = NodeNumber;
            distance[destinationNodeNumber] = distance[NodeNumber] + moveCost;
        }
    }


    private boolean hasSameUnitInHex(int x, int y) {
        if (this instanceof CivilUnit) {
            if (Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
                return false;
        } else if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null)
            return false;
        return true;
    }


    private int minKey(int[] key, Boolean[] mstSet, int numberOfNodes) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numberOfNodes; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    public void resetMovement() {
        this.remainingMovement = this.movementSpeed;
    }

    public int getCost() {
        return cost;
    }

    abstract public boolean needsCommand();

    abstract public void cancelMission();

}
