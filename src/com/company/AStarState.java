package com.company;
import java.util.*;
import java.util.HashMap;
import java.util.Set;

public class AStarState
{
    Map<Location, Waypoint> openPoint = new HashMap<>();
    Map<Location, Waypoint> closePoint = new HashMap<>();
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }


    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0)
            return null;

        Waypoint best = null;
        Set<Location> openPointKeys = openPoint.keySet();
        Iterator i = openPointKeys.iterator();
        float bestCost = Float.MAX_VALUE;

        while (i.hasNext()){
            Location location = (Location)i.next();
            Waypoint waypoint = openPoint.get(location);
            float pointTotalCost = waypoint.getTotalCost();
            if (pointTotalCost < bestCost){
                best = openPoint.get(location);
                bestCost = pointTotalCost;
            }
        }
        return best;
    }


    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location location = newWP.getLocation();

        if (openPoint.containsKey(location))
        {
            Waypoint current_waypoint = openPoint.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                openPoint.put(location, newWP);
                return true;
            }
            return false;
        }
        openPoint.put(location, newWP);
        return true;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openPoint.size();
    }



    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = openPoint.remove(loc);
        closePoint.put(loc, waypoint);
    }


    public boolean isLocationClosed(Location loc)
    {
        return closePoint.containsKey(loc);
    }
}
