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
	
	public List<List<Neighbor>> getAllPaths(Neighbor departure, Neighbor destination){
		List<List<Neighbor>> result = new ArrayList<List<Neighbor>>();
		if(departure.getStop_id()== destination.getStop_id()){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> after if");
			List<Neighbor> temp = new ArrayList<Neighbor>();
			temp.add(departure);
			result.add(temp);
			return result;
		}
		Map<String, Boolean>visited = new HashMap<String, Boolean>();
		Deque<Neighbor> path = new ArrayDeque<Neighbor>();
		getAllPathsDFS(departure, destination, visited, path, result);
		return result;
	}
	
	void getAllPathsDFS(Neighbor departure, Neighbor destination, Map<String, Boolean>visited, Deque<Neighbor> path, List<List<Neighbor>> result){
		visited.put(departure.getStop_id()+"_"+departure.getTrip_id(), true) ; // Mark visited
		path.add(departure); // Add to the end
		if(departure.getStop_id() == destination.getStop_id()){
			result.add(new ArrayList<Neighbor>(path));
		}
		else{
			if(neighbors.containsKey(departure.getStop_id())){
				for(Neighbor stop : neighbors.get(departure.getStop_id())){
					System.out.println(stop.getName());
					if(!visited.get(stop.getStop_id()+"_"+stop.getTrip_id())){
						getAllPathsDFS(stop, destination, visited, path, result);
					}
				}
			}
		}
		path.removeLast();
		visited.put(destination.getStop_id()+"_"+destination.getTrip_id(), false);
	}

}
