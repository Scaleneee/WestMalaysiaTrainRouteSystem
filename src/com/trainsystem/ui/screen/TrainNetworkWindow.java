package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainEdge;
import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrainNetworkWindow extends JFrame {

    private final TrainGraph graph;

    public TrainNetworkWindow(TrainGraph graph) {
        this.graph = graph;

        setTitle("Train Network");
        setSize(1250, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel =
                new JLabel("WEST MALAYSIA TRAIN NETWORK",
                        SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 10, 10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        NetworkPanel networkPanel =
                new NetworkPanel(graph);

        add(networkPanel, BorderLayout.CENTER);

        JLabel legend = new JLabel(
                "Station = Vertex     |     Line = Direct Route     |     Click a station to view its routes",
                SwingConstants.CENTER
        );

        legend.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 10, 12, 10
                )
        );

        add(legend, BorderLayout.SOUTH);
    }

    public static void showNetwork(TrainGraph graph) {

        SwingUtilities.invokeLater(() -> {

            TrainNetworkWindow window =
                    new TrainNetworkWindow(graph);

            window.setVisible(true);
        });
    }

    private static class NetworkPanel extends JPanel {

        private static final int NODE_RADIUS = 24;

        private final TrainGraph graph;

        private final Map<String, Point2D.Double>
                stationPositions = new HashMap<>();

        private Station selectedStation;

        public NetworkPanel(TrainGraph graph) {

            this.graph = graph;

            setBackground(Color.WHITE);

            setToolTipText("");

            createStationPositions();

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    selectedStation =
                            findStationAt(e.getPoint());

                    repaint();
                }
            });
        }

        private void createStationPositions() {

            stationPositions.put(
                    "PAD",
                    new Point2D.Double(0.08, 0.10)
            );

            stationPositions.put(
                    "ARA",
                    new Point2D.Double(0.22, 0.10)
            );

            stationPositions.put(
                    "ALS",
                    new Point2D.Double(0.36, 0.10)
            );

            stationPositions.put(
                    "SPG",
                    new Point2D.Double(0.50, 0.10)
            );

            stationPositions.put(
                    "TGS",
                    new Point2D.Double(0.64, 0.10)
            );

            stationPositions.put(
                    "BUT",
                    new Point2D.Double(0.22, 0.27)
            );

            stationPositions.put(
                    "BM",
                    new Point2D.Double(0.42, 0.27)
            );

            stationPositions.put(
                    "TAS",
                    new Point2D.Double(0.62, 0.27)
            );

            stationPositions.put(
                    "IPH",
                    new Point2D.Double(0.80, 0.31)
            );

            stationPositions.put(
                    "BDR",
                    new Point2D.Double(0.47, 0.45)
            );

            stationPositions.put(
                    "KKB",
                    new Point2D.Double(0.70, 0.47)
            );

            stationPositions.put(
                    "KLS",
                    new Point2D.Double(0.58, 0.60)
            );

            stationPositions.put(
                    "SRM",
                    new Point2D.Double(0.58, 0.72)
            );

            stationPositions.put(
                    "GMS",
                    new Point2D.Double(0.50, 0.86)
            );

            stationPositions.put(
                    "SEG",
                    new Point2D.Double(0.66, 0.86)
            );

            stationPositions.put(
                    "KLV",
                    new Point2D.Double(0.80, 0.86)
            );

            stationPositions.put(
                    "JHB",
                    new Point2D.Double(0.93, 0.86)
            );
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            drawRoutes(g2);

            drawStations(g2);

            g2.dispose();
        }

        private void drawRoutes(Graphics2D g2) {

            Set<String> drawnPairs =
                    new HashSet<>();

            List<Station> stations =
                    new ArrayList<>(
                            graph.getVertices()
                    );

            for (Station source : stations) {

                List<TrainEdge<Station>> routes =
                        new ArrayList<>(
                                graph.getEdges(source)
                        );

                for (TrainEdge<Station> route : routes) {

                    Station destination =
                            route.getDestination();

                    String sourceCode =
                            source.getStationCode();

                    String destinationCode =
                            destination.getStationCode();

                    String pairKey;

                    if (sourceCode.compareTo(
                            destinationCode) < 0) {

                        pairKey =
                                sourceCode
                                        + "-"
                                        + destinationCode;

                    } else {

                        pairKey =
                                destinationCode
                                        + "-"
                                        + sourceCode;
                    }

                    if (drawnPairs.contains(pairKey)) {
                        continue;
                    }

                    drawnPairs.add(pairKey);

                    Point sourcePoint =
                            getStationPoint(source);

                    Point destinationPoint =
                            getStationPoint(destination);

                    boolean highlighted =
                            selectedStation != null
                                    &&
                                    (
                                            selectedStation.equals(source)
                                                    ||
                                                    selectedStation.equals(destination)
                                    );

                    if (highlighted) {

                        g2.setColor(
                                new Color(
                                        25,
                                        118,
                                        210
                                )
                        );

                        g2.setStroke(
                                new BasicStroke(3f)
                        );

                    } else {

                        g2.setColor(
                                new Color(
                                        185,
                                        185,
                                        185
                                )
                        );

                        g2.setStroke(
                                new BasicStroke(1.4f)
                        );
                    }

                    g2.drawLine(
                            sourcePoint.x,
                            sourcePoint.y,
                            destinationPoint.x,
                            destinationPoint.y
                    );

                    boolean reverseExists =
                            graph.containsEdge(
                                    destination,
                                    source
                            );

                    drawArrowHead(
                            g2,
                            sourcePoint,
                            destinationPoint
                    );

                    if (reverseExists) {

                        drawArrowHead(
                                g2,
                                destinationPoint,
                                sourcePoint
                        );
                    }
                }
            }
        }

        private void drawArrowHead(
                Graphics2D g2,
                Point from,
                Point to) {

            double angle =
                    Math.atan2(
                            to.y - from.y,
                            to.x - from.x
                    );

            int endX =
                    (int) (
                            to.x
                                    -
                                    NODE_RADIUS
                                            *
                                            Math.cos(angle)
                    );

            int endY =
                    (int) (
                            to.y
                                    -
                                    NODE_RADIUS
                                            *
                                            Math.sin(angle)
                    );

            int arrowSize = 9;

            double leftAngle =
                    angle - Math.PI / 7;

            double rightAngle =
                    angle + Math.PI / 7;

            int x1 =
                    (int) (
                            endX
                                    -
                                    arrowSize
                                            *
                                            Math.cos(leftAngle)
                    );

            int y1 =
                    (int) (
                            endY
                                    -
                                    arrowSize
                                            *
                                            Math.sin(leftAngle)
                    );

            int x2 =
                    (int) (
                            endX
                                    -
                                    arrowSize
                                            *
                                            Math.cos(rightAngle)
                    );

            int y2 =
                    (int) (
                            endY
                                    -
                                    arrowSize
                                            *
                                            Math.sin(rightAngle)
                    );

            Polygon arrow = new Polygon();

            arrow.addPoint(endX, endY);

            arrow.addPoint(x1, y1);

            arrow.addPoint(x2, y2);

            g2.fillPolygon(arrow);
        }

        private void drawStations(Graphics2D g2) {

            List<Station> stations =
                    new ArrayList<>(
                            graph.getVertices()
                    );

            for (Station station : stations) {

                Point point =
                        getStationPoint(station);

                boolean selected =
                        station.equals(
                                selectedStation
                        );

                if (selected) {

                    g2.setColor(
                            new Color(
                                    25,
                                    118,
                                    210
                            )
                    );

                } else {

                    g2.setColor(
                            new Color(
                                    33,
                                    33,
                                    33
                            )
                    );
                }

                g2.fillOval(
                        point.x - NODE_RADIUS,
                        point.y - NODE_RADIUS,
                        NODE_RADIUS * 2,
                        NODE_RADIUS * 2
                );

                g2.setColor(Color.WHITE);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                12
                        )
                );

                String code =
                        station.getStationCode();

                FontMetrics codeMetrics =
                        g2.getFontMetrics();

                int codeX =
                        point.x
                                -
                                codeMetrics.stringWidth(code)
                                        / 2;

                int codeY =
                        point.y
                                +
                                codeMetrics.getAscent()
                                        / 2
                                -
                                2;

                g2.drawString(
                        code,
                        codeX,
                        codeY
                );

                g2.setColor(Color.BLACK);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                11
                        )
                );

                String name =
                        station.getStationName();

                FontMetrics nameMetrics =
                        g2.getFontMetrics();

                int nameX =
                        point.x
                                -
                                nameMetrics.stringWidth(name)
                                        / 2;

                int nameY =
                        point.y
                                +
                                NODE_RADIUS
                                +
                                16;

                g2.drawString(
                        name,
                        nameX,
                        nameY
                );
            }
        }

        private Point getStationPoint(
                Station station) {

            Point2D.Double normalized =
                    stationPositions.get(
                            station.getStationCode()
                    );

            if (normalized != null) {

                int x =
                        60
                                +
                                (int) (
                                        normalized.x
                                                *
                                                Math.max(
                                                        100,
                                                        getWidth() - 120
                                                )
                                );

                int y =
                        40
                                +
                                (int) (
                                        normalized.y
                                                *
                                                Math.max(
                                                        100,
                                                        getHeight() - 90
                                                )
                                );

                return new Point(x, y);
            }

            int hash =
                    Math.abs(
                            station
                                    .getStationCode()
                                    .hashCode()
                    );

            int x =
                    70
                            +
                            (
                                    hash
                                            %
                                            Math.max(
                                                    1,
                                                    getWidth() - 140
                                            )
                            );

            int y =
                    70
                            +
                            (
                                    (hash / 100)
                                            %
                                            Math.max(
                                                    1,
                                                    getHeight() - 140
                                            )
                            );

            return new Point(x, y);
        }

        private Station findStationAt(
                Point mousePoint) {

            for (Station station :
                    graph.getVertices()) {

                Point stationPoint =
                        getStationPoint(station);

                double distance =
                        mousePoint.distance(
                                stationPoint
                        );

                if (distance
                        <= NODE_RADIUS + 5) {

                    return station;
                }
            }

            return null;
        }

        @Override
        public String getToolTipText(
                MouseEvent event) {

            Station station =
                    findStationAt(
                            event.getPoint()
                    );

            if (station == null) {
                return null;
            }

            int routeCount =
                    graph.getDegree(station);

            return station.getStationCode()
                    + " - "
                    + station.getStationName()
                    + " | Outgoing routes: "
                    + routeCount;
        }
    }
}