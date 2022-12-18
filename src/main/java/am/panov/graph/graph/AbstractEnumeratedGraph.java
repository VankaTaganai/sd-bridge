package am.panov.graph.graph;

import java.util.List;

public interface AbstractEnumeratedGraph {
    int countVertices();
    List<Edge> getEdges();
}
