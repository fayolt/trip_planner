package com.sm.tripplanner.helper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sm.tripplanner.Neighbor;
import com.sm.tripplanner.Stop;

public class Graph {
	
	public int stops;
	public Map<Integer, List<Neighbor>> neighbors; // Adjacency list
	
	public Graph(int stops){
		this.stops = stops;
		this.neighbors = new HashMap<Integer, List<Neighbor>>();
	}
	
	public void addEdge(Stop departure, Neighbor destination){
		if(!neighbors.containsKey(departure.getStop_id())){
			neighbors.put(departure.getStop_id(), new ArrayList<Neighbor>());
		}
		neighbors.get(departure.getStop_id()).add(destination);
	}
	
	public List<List<Stop>> getAllPaths(Stop departure, Stop destination){
		List<List<Stop>> result = new ArrayList<List<Stop>>();
		if(departure.getStop_id()== destination.getStop_id()){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after if");
			List<Stop> temp = new ArrayList<Stop>();
			temp.add(departure);
			result.add(temp);
			return result;
		}
		Map<Integer, Boolean>visited = new HashMap<Integer, Boolean>();
		Deque<Stop> path = new ArrayDeque<Stop>();
		getAllPathsDFS(departure, destination, visited, path, result);
		return result;
	}
	
	void getAllPathsDFS(Stop departure, Stop destination, Map<Integer, Boolean>visited, Deque<Stop> path, List<List<Stop>> result){
		visited.put(departure.getStop_id(), true) ; // Mark visited
		path.add(departure); // Add to the end
		if(departure.getStop_id() == destination.getStop_id()){
			result.add(new ArrayList<Stop>(path));
		}
		else{
			if(neighbors.containsKey(departure.getStop_id())){
				for(Stop stop : neighbors.get(departure.getStop_id())){
					if(!visited.get(stop.getStop_id())){
						getAllPathsDFS(stop, destination, visited, path, result);
					}
				}
			}
		}
		path.removeLast();
		visited.put(destination.getStop_id(), false);
	}

}
