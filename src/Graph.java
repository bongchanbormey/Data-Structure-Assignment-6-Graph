import java.util.*;

public class Graph {
    private SeparateChaining<List<Integer>> adjacencyList;

    // The Graph class must have a constructor that accepts a set of vertices and a set of edges, as strings. The constructor adds this set of edges and vertices to the graph.
    // public Graph(String verticesSet, String edgeSet)
    // The String verticesSet following this format: "{0, 1, 2, 3, 4}"
    // The String edgeSet following this format: "{(0,1), (0,2), (1,3), (2,3), (3,4)}"


    // Constructor

    public Graph(String verticesSet, String edgeSet) {
        // Parse vertices and ensure there is at least one vertex
        List<Integer> vertices = parseVertices(verticesSet);
        int numVertices = Math.max(vertices.size(), 1);
        this.adjacencyList = new SeparateChaining<>(numVertices, 10); // Adjust chain capacity as needed

        // Add vertices
        for (int vertex : vertices) {
            addVertex(vertex);
        }

        // Add edges
        for (int[] edge : parseEdges(edgeSet)) {
            addEdge(edge[0], edge[1]);
        }
    }

    // Parse vertices from input string
    private List<Integer> parseVertices(String verticesSet) {
        // Clean the string input
        verticesSet = verticesSet.replaceAll("[{}\\s]", "");
        if (verticesSet.isEmpty()) return new ArrayList<>();
        // Split the string by comma and parse into integers
        String[] vertices = verticesSet.split(",");
        List<Integer> result = new ArrayList<>();
        for (String vertex : vertices) {
            result.add(Integer.parseInt(vertex));
        }
        return result;
    }

    // Parse edges from input string
    private List<int[]> parseEdges(String edgeSet) {
        // Clean the string input
        edgeSet = edgeSet.replaceAll("[{}\\s]", "");
        if (edgeSet.isEmpty()) return new ArrayList<>();
        // Split the edges
        String[] edges = edgeSet.split("\\),\\(");
        List<int[]> result = new ArrayList<>();
        for (String edge : edges) {
            // Parse the source+destination into integers
            edge = edge.replaceAll("[()]", "");
            String[] vertices = edge.split(",");
            result.add(new int[]{Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1])});
        }
        return result;
    }

    // (a). Add a vertex
    // Time complexity: O(n)
    public void addVertex(int vertex) {
        if (adjacencyList.search(String.valueOf(vertex)) == null) {
            adjacencyList.insert(String.valueOf(vertex), new ArrayList<>());
        }
    }

    // (b). Add an edge
    // Time complexity: O(n)
    public void addEdge(int source, int destination) {
        List<Integer> sourceNeighbors = adjacencyList.search(String.valueOf(source));
        List<Integer> destNeighbors = adjacencyList.search(String.valueOf(destination));

        if (sourceNeighbors == null || destNeighbors == null) {
            throw new IllegalArgumentException("One or both vertices do not exist.");
        }

        if (!sourceNeighbors.contains(destination)) {
            sourceNeighbors.add(destination);
        }
        if (!destNeighbors.contains(source)) {
            destNeighbors.add(source);
        }
    }

    // (c). Remove a vertex
    // Time complexity: O(n^2)
    public void removeVertex(int vertex) {
        for (int i = 0; i < adjacencyList.size(); i++) {
            List<Integer> neighbors = adjacencyList.search(String.valueOf(i));
            neighbors.remove(Integer.valueOf(vertex));      // Remove vertex from all adjacency lists
        }
        adjacencyList.delete(String.valueOf(vertex));       // Delete the vertex's entry in the adjacency list
    }

    // (d). Remove an edge
    // Time complexity: O(1)
    public void removeEdge(int source, int destination) {
        List<Integer> sourceNeighbors = adjacencyList.search(String.valueOf(source));
        List<Integer> destNeighbors = adjacencyList.search(String.valueOf(destination));

        if (sourceNeighbors != null) {
            sourceNeighbors.remove(Integer.valueOf(destination));
        }
        if (destNeighbors != null) {
            destNeighbors.remove(Integer.valueOf(source));
        }
    }

    // (e). Get neighbors of a vertex
    // Time complexity: O(k)
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = adjacencyList.search(String.valueOf(vertex));
        return neighbors != null ? neighbors : new ArrayList<>();
    }

    // (f). Get the number of neighbors of a vertex
    // Time complexity:
    public int getDecree(int vertex) {
        List<Integer> neighbors = adjacencyList.search(String.valueOf(vertex));
        return (neighbors != null) ? neighbors.size() : 0;
    }

    // (g). Check if it's a cyclic graph
    // Time complexity: O(V + E)
    public boolean isCyclicGraph() {
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < adjacencyList.size(); i++) {
            if (!visited.contains(i)) {
                if (isCyclicUtil(i, -1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCyclicUtil(int vertex, int parent, Set<Integer> visited) {
        visited.add(vertex);
        List<Integer> neighbors = adjacencyList.search(String.valueOf(vertex));

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (isCyclicUtil(neighbor, vertex, visited)) {
                        return true;
                    }
                } else if (neighbor != parent) {
                    return true;
                }
            }
        }
        return false;
    }

    // (h).return the shortest path between two vertices
    // Time complexity: O(V + E) // Using Breadth-First Search(BFS)
    public List<Integer> getShortestPath(int source, int destination) {
        if (adjacencyList.search(String.valueOf(source)) == null ||
                adjacencyList.search(String.valueOf(destination)) == null) {
            throw new IllegalArgumentException("One or both vertices do not exist.");
        }

        Map<Integer, Integer> previous = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(source);
        visited.add(source);
        previous.put(source, null); // Root has no parent

        // BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == destination) break;

            // Get the neighbors using SeparateChaining and UnorderedArray
            List<Integer> neighbors = adjacencyList.search(String.valueOf(current));
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        // Construct the shortest path
        List<Integer> path = new ArrayList<>();
        Integer step = destination;
        while (step != null) {
            path.add(0, step);
            step = previous.get(step);
        }

        // If the source is not reached, there is no path
        if (path.get(0) != source) {
            return new ArrayList<>();
        }

        return path;
    }

    // (i). Get the Minimum Spanning Tree (MST)
    // Time complexity:O(E Log E) //Using Kruskal's algorithm
    public Graph getMSTGraph() {
        Graph mst = new Graph("{}", "{}"); // Create a new Graph object for the MST
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        // Start from the first vertex in the graph (vertex 0 as an example)
        List<Integer> vertices = parseVertices("{0, 1, 2, 3, 4}"); // Adjust if vertices parsing available
        if (vertices.isEmpty()) return mst;

        int startVertex = vertices.get(0);
        visited.add(startVertex);

        // Add all edges of the start vertex to the priority queue
        List<Integer> neighbors = adjacencyList.search(String.valueOf(startVertex));
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                edgeQueue.offer(new Edge(startVertex, neighbor, 1)); // Weight is 1 for unweighted graph
            }
        }

        // Process the queue until all vertices are included in the MST
        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int source = edge.source, destination = edge.destination;

            // If the destination vertex hasn't been visited, add it to the MST
            if (!visited.contains(destination)) {
                visited.add(destination);
                mst.addVertex(source);
                mst.addVertex(destination);
                mst.addEdge(source, destination);

                // Add all edges of the newly visited vertex
                List<Integer> destNeighbors = adjacencyList.search(String.valueOf(destination));
                if (destNeighbors != null) {
                    for (int neighbor : destNeighbors) {
                        if (!visited.contains(neighbor)) {
                            edgeQueue.offer(new Edge(destination, neighbor, 1)); // Weight is 1
                        }
                    }
                }
            }
        }

        return mst;
    }

    // (j). Print the graph in readable format
    public void printGraph() {
        System.out.println("Graph Adjacency List:");

        // We iterate over the vertices that exist in the adjacency list.
        for (int i = 0; i < adjacencyList.size(); i++) {
            // Check if the vertex exists in the adjacency list (is not null).
            List<Integer> neighbors = adjacencyList.search(String.valueOf(i));

            // Only print the vertex if it exists in the adjacency list.
            if (neighbors != null) {
                // If neighbors are found, print them.
                if (!neighbors.isEmpty()) {
                    System.out.print("Vertex " + i + ": ");
                    for (int neighbor : neighbors) {
                        System.out.print(neighbor + " ");
                    }
                    System.out.println();  // Move to the next line
                } else {
                    // If no neighbors exist, print that the vertex has no neighbors.
                    System.out.println("Vertex " + i + " has no neighbor.");
                }
            }
        }
    }
}

