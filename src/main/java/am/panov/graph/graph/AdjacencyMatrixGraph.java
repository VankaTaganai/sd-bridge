package am.panov.graph.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AdjacencyMatrixGraph implements AbstractEnumeratedGraph{
    List<List<Boolean>> adj;

    private void emptyGraph(int nVertices) {
        adj = new ArrayList<>(nVertices);

        for (int i = 0; i < nVertices; ++i) {
            adj.add(new ArrayList<>(nVertices));
            for (int j = 0; j < nVertices; ++j) {
                adj.get(i).add(false);
            }
        }
    }

    public AdjacencyMatrixGraph(List<Integer> rowEdges) {
        this(rowEdges.toArray(new Integer[0]));
    }

    public AdjacencyMatrixGraph(Integer... rowEdges) {
        if (rowEdges.length % 2 == 1) {
            throw new IllegalArgumentException("Incomplete edges array: odd amount of vertices");
        }

        int nVertices = Stream.of(rowEdges).max(Comparator.naturalOrder()).get() + 1;
        emptyGraph(nVertices);

        for (int i = 0; i < rowEdges.length; i += 2) {
            if (rowEdges[i] < 0 || rowEdges[i + 1] < 0) {
                throw new IllegalArgumentException("Vertex numbers should be non-negative");
            }
            adj.get(rowEdges[i]).set(rowEdges[i + 1], true);
            adj.get(rowEdges[i + 1]).set(rowEdges[i], true);
        }
    }

    @Override
    public int countVertices() {
        return adj.size();
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < adj.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (adj.get(i).get(j)) {
                    edges.add(new Edge(i, j));
                }
            }
        }

        return edges;
    }
}
