import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Test 1: Adding vertices
        System.out.println("Adding vertices...");
        Graph graph = new Graph("{0, 1, 2, 3, 4}", "{(0,1), (0,2), (1,3), (2,3),(3,4) }");
        graph.printGraph();

        // Test 2: Add more vertices
        System.out.println("\nAdding more vertices...");
        graph.addVertex(4);
        graph.printGraph();

        // Test 3: Adding edges
        System.out.println("\nAdding edges...");
        graph.addEdge(3, 4);
        graph.printGraph();

        // Test 4: Removing a vertex
        System.out.println("\nRemoving vertex 2...");
        graph.removeVertex(2);
        graph.printGraph();

        // Test 5: Removing an edge
        System.out.println("\nRemoving edge (1, 0)...");
        graph.removeEdge(1, 0);
        graph.printGraph();

        // Test 6: Get neighbors of vertex 1 and 3
        System.out.println("\nNeighbors of vertex 1:");
        System.out.println(graph.getNeighbors(1));
        System.out.println(graph.getDecree(1));

        // Test 7: Get neighbors of vertex 3
        System.out.println("\nNeighbors of vertex 3:");
        System.out.println(graph.getNeighbors(3));

        // Test 8: Try removing an edge that doesn't exist
        System.out.println("\nAttempting to remove a non-existent edge (1, 2)...");
        graph.removeEdge(1, 2); // Should not affect the graph
        graph.printGraph();

        // Test 9: Get decree - return a number of neighbors of a vertex
        System.out.println("\nAdd new vertex and edges:");
        graph.addVertex(5);
        graph.addEdge(3, 5);
        graph.addEdge(1, 5);
        graph.printGraph();

        System.out.println("\nNumber of neighbors of vertex 4: " + graph.getDecree(4));
//        vertex 4 (index) = node 5

        // Test 10: Check if it is cyclic
        System.out.println("Degree of vertex 3: " + graph.getDecree(3));
        System.out.println("Is the graph cyclic? " + graph.isCyclicGraph());

        // Test 11: Shortest path from vertex 0 to 4
        System.out.println("\nShortest path from 0 to 4:");
        List<Integer> shortestPath = graph.getShortestPath(0, 4);
        if (shortestPath.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path: " + shortestPath);
        }

        // Test 12: Minimum Spanning Tree (MST)
        System.out.println("\nMinimum Spanning Tree (MST):");
        Graph mst = graph.getMSTGraph();
        graph.printGraph();
    }
}