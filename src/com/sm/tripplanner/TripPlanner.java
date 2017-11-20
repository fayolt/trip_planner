package com.sm.tripplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sm.tripplanner.helper.Graph;

public class TripPlanner {
	List<Plan> plans = new ArrayList<>();
    private final List<Neighbor> edges;
    private Set<Stop> settledNodes;
    private Set<Stop> unSettledNodes;
    private Map<Stop, Stop> predecessors;
    private Map<Stop, Long> distance;

    public TripPlanner(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.edges = new ArrayList<Neighbor>(graph.getVertices());
    }

    public void execute(Stop source) {
        settledNodes = new HashSet<Stop>();
        unSettledNodes = new HashSet<Stop>();
        distance = new HashMap<Stop, Long>();
        predecessors = new HashMap<Stop, Stop>();
        distance.put(source, 0L);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Stop node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Stop node) {
        List<Stop> adjacentNodes = getNeighbors(node);
        for (Stop target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    public long getDistance(Stop node, Stop target) {
        for (Neighbor edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getTravel_time();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Stop> getNeighbors(Stop node) {
        List<Stop> neighbors = new ArrayList<Stop>();
        for (Neighbor edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Stop getMinimum(Set<Stop> vertexes) {
    	
        Stop minimum = null;
        for (Stop vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Stop vertex) {
        return settledNodes.contains(vertex);
    }

    private long getShortestDistance(Stop destination) {
        Long d = distance.get(destination);
        if (d == null) {
            return Long.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Stop> getOptimalTrip(Stop target) {
        LinkedList<Stop> path = new LinkedList<Stop>();
        Stop step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        
        
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
