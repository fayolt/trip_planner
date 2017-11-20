package com.sm.tripplanner.helper;

import java.util.List;

import com.sm.tripplanner.Neighbor;
import com.sm.tripplanner.Stop;

public class Graph {

	private final List<Neighbor> vertices;
	private final List<Stop> edges;

	public Graph(List<Neighbor> vertices, List<Stop> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public List<Neighbor> getVertices() {
		return vertices;
	}

	public List<Stop> getEdges() {
		return edges;
	}

}
