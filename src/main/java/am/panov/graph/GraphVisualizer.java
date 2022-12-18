package am.panov.graph;

import am.panov.graph.api.DrawingApi;
import am.panov.graph.graph.AbstractEnumeratedGraph;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphVisualizer {
    private static final double CIRCLE_RADIUS_PT = 10;
    private static final double TEXT_ADDITIONAL_DIST_PT = 25;

    private final DrawingApi drawingApi;

    public GraphVisualizer(final DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    private List<Point2D> locateVerticesAndDrawText(int nVertices, long width, long height) {
        final double radius = Math.min(width, height) / 3.0;
        final double x0 = width / 2.0;
        final double y0 = height / 2.0;

        final List<Point2D> vertices = new ArrayList<>();

        for (int i = 0; i < nVertices; ++i) {
            final double angle = 2 * Math.PI * i / nVertices;
            drawingApi.drawText(String.valueOf(i),
                    x0 + (radius + TEXT_ADDITIONAL_DIST_PT) * Math.cos(angle),
                    y0 + (radius + TEXT_ADDITIONAL_DIST_PT) * Math.sin(angle));
            vertices.add(new Point2D.Double(x0 + radius * Math.cos(angle), y0 + radius * Math.sin(angle)));
        }

        return vertices;
    }

    public void draw(AbstractEnumeratedGraph graph) {
        final long width = drawingApi.getDrawingAreaWidth();
        final long height = drawingApi.getDrawingAreaHeight();

        final List<Point2D> verticesCenters = locateVerticesAndDrawText(graph.countVertices(), width, height);

        verticesCenters.forEach(center -> drawingApi.drawCircle(center.getX(), center.getY(), CIRCLE_RADIUS_PT));
        graph.getEdges().forEach(e -> {
            Point2D c1 = verticesCenters.get(e.first());
            Point2D c2 = verticesCenters.get(e.second());
            drawingApi.drawLine(c1.getX(), c1.getY(),  c2.getX(), c2.getY());
        });

        drawingApi.showResult();
    }
}
