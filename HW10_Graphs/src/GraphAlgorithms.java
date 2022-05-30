import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Shreshta Yadav
 * @userid syadav73
 * @GTID 903866946
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("inputs are null");
        }
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        List<Vertex<T>> output = new ArrayList<Vertex<T>>();
        Queue<Vertex<T>> q = new LinkedList<Vertex<T>>();
        q.add(start);
        visitedList.add(start);
        output.add(start);
        while (!q.isEmpty()) {
            Vertex<T> curr = q.remove();
            for (VertexDistance<T> vertex: graph.getAdjList().get(curr)) {
                if (!(visitedList.contains(vertex.getVertex()))) {
                    output.add(vertex.getVertex());
                    visitedList.add(vertex.getVertex());
                    q.add(vertex.getVertex());
                }
            }
        }
        return output;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("inputs are null");
        }
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        List<Vertex<T>> output = new ArrayList<Vertex<T>>();
        dfsHelper(output, visitedList, start, graph);
        return output;
    }

    /**
     * helper method that performs recursion for the dfs method.
     * @param output list of all the vertices traversed.
     * @param visitedList list of all the vertices visited.
     * @param curr current vertex being compared.
     * @param graph graph that is being traversed.
     * @param <T> the generic typing of the data.
     */
    private static <T> void dfsHelper(List<Vertex<T>> output,
                                      List<Vertex<T>> visitedList, Vertex<T> curr, Graph<T> graph) {
        visitedList.add(curr);
        output.add(curr);
        for (VertexDistance<T> vertex: graph.getAdjList().get(curr)) {
            if (!(visitedList.contains(vertex.getVertex()))) {
                dfsHelper(output, visitedList, vertex.getVertex(), graph);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("inputs are null");
        }
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        Map<Vertex<T>, Integer> output = new HashMap<Vertex<T>, Integer>();
        Queue<VertexDistance<T>> q = new PriorityQueue<VertexDistance<T>>();
        for (Vertex<T> vertex : graph.getVertices()) {
            if (vertex.equals(start)) {
                output.put(vertex, 0);
            } else {
                output.put(vertex, Integer.MAX_VALUE);
            }
        }
        q.add(new VertexDistance<T>(start, 0));
        while (!(q.isEmpty()) && visitedList.size() != graph.getVertices().size()) {
            VertexDistance<T> curr = q.remove();
            if (!(visitedList.contains(curr.getVertex()))) {
                visitedList.add(curr.getVertex());
                output.put(curr.getVertex(), curr.getDistance());
                for (VertexDistance<T> vertex: graph.getAdjList().get(curr.getVertex())) {
                    if (!(visitedList.contains(vertex.getVertex()))) {
                        q.add(new VertexDistance<T>(vertex.getVertex(), (curr.getDistance()) + vertex.getDistance()));
                    }
                }
            }
        }
        return output;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("inputs are null");
        }
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        Set<Edge<T>> output = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> q = new PriorityQueue<Edge<T>>();
        for (VertexDistance<T> vertexDistance : graph.getAdjList().get(start)) {
            q.add(new Edge<T>(start, vertexDistance.getVertex(), vertexDistance.getDistance()));
        }
        visitedList.add(start);
        while (!(q.isEmpty()) && visitedList.size() != graph.getVertices().size()) {
            Edge<T> curr = q.remove();
            if (!(visitedList.contains(curr.getV()))) {
                visitedList.add(curr.getV());
                output.add(new Edge<T>(curr.getU(), curr.getV(), curr.getWeight()));
                output.add(new Edge<T>(curr.getV(), curr.getU(), curr.getWeight()));
                for (VertexDistance<T> vertexDistance : graph.getAdjList().get(curr.getV())) {
                    if (!visitedList.contains(vertexDistance.getVertex())) {
                        q.add(new Edge<T>(curr.getV(), vertexDistance.getVertex(), vertexDistance.getDistance()));
                    }
                }
            }
        }
        if (output.size() < graph.getVertices().size() - 1) {
            return null;
        }
        return output;
    }
}