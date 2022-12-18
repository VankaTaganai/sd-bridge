package am.panov.graph.graph;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class EdgeListGraph implements AbstractEnumeratedGraph {
    private int nVertices;
    private final List<Edge> edges;

    public EdgeListGraph(List<Integer> rowEdges) {
        this(rowEdges.toArray(new Integer[0]));
    }

    public EdgeListGraph(Integer... rowEdges) {
        if (rowEdges.length % 2 == 1) {
            throw new IllegalArgumentException("Incomplete edges array: odd amount of vertices");
        }
        nVertices = 0;
        edges = new ArrayList<>();
        for (int i = 0; i < rowEdges.length; i += 2) {
            if (rowEdges[i] < 0 || rowEdges[i + 1] < 0) {
                throw new IllegalArgumentException("Vertex numbers should be non-negative");
            }
            edges.add(new Edge(rowEdges[i], rowEdges[i + 1]));
            nVertices = max(nVertices, rowEdges[i] + 1);
            nVertices = max(nVertices, rowEdges[i + 1] + 1);
        }
    }

    @Override
    public int countVertices() {
        return nVertices;
    }

    @Override
    public List<Edge> getEdges() {
        return List.copyOf(edges);
    }
}
