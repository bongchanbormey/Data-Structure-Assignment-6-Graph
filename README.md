# Data-Structure-Assignment-6-Graph

### Project Requirements:
1. The Graph class is an unweighted, undirected graph using the adjacency list. The adjacency list should be a Hash Table using Separate Chaining, use the vertex as the key.
Must make use of your HashTable interface using the SeperateChaining implementation. You may make use of any previous data structure you implemented and may make any modifications to those classes.
2. The Graph class must have the following methods:
- (a) public void addVertex(int vertex)
For simplicity let’s make our vertex a non-negative Integer. This method adds a new vertex to our graph. The vertices in our graph should be unique meaning there should not be duplicates.
- (b) public void addEdge(int source, int destination)
This method adds a new edge to our graph. The edges in our graph should also be unique.
- (c) public void removeVertex(int vertex)
Method to remove a vertex from the graph. Handle removing appropriate edges as well.
- (d) public void removeEdge(int source, int destination) Method to remove an edge from the graph.
- (e) public List<Integer> getNeighbors(int vertex)
Method to return a list of vertices that are neighbors of a vertex.
- (f) public int getDecree(int vertex)
Method to return the number of neighbors of a vertex.
- (g) public boolean isCyclicGraph()
Method to check whether this graph is a cyclic graph (have circles).
- (h) public List<Integer> getShortestPath(int source, int destination) Method to return the shortest path between two vertex, as a list of vertices.
- (i) public Graph getMSTGraph()
Method to return the Minimum Spanning Tree of this graph. Return it as a graph object.
- (j) public void printGraph()
Method to print the graph. This should just print the adjacency list and other info on this graph. Print it in a way that is user-friendly to read.
3. The Graph class must have a constructor that accepts a set of vertices and a set of edges, as strings. The constructor adds this set of edges and vertices to the graph.

public Graph(String verticesSet, String edgeSet)

The String verticesSet following this format:
     "{0, 1, 2, 3, 4}"
     
The String edgeSet following this format:
     "{(0,1), (0,2), (1,3), (2,3), (3,4)}"
