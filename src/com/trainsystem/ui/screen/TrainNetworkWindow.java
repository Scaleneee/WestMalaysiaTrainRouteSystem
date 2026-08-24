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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * display the train graph network in a new window using Swing
 */
public class TrainNetworkWindow extends JFrame {

    public TrainNetworkWindow(TrainGraph graph) {

        setTitle("West Malaysia Train Network");
        setSize(1450, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(
                "West Malaysia Train Network",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        10,
                        10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        NetworkPanel networkPanel =
                new NetworkPanel(graph);

        JScrollPane scrollPane =
                new JScrollPane(networkPanel);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        scrollPane.getHorizontalScrollBar()
                .setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        JLabel instructionLabel = new JLabel(
                "Click a station to show its routes, duration and price",
                SwingConstants.CENTER
        );

        instructionLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        instructionLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        10,
                        10
                )
        );

        add(instructionLabel, BorderLayout.SOUTH);
    }


    public static void showNetwork(TrainGraph graph) {

        SwingUtilities.invokeLater(() -> {

            TrainNetworkWindow window =
                    new TrainNetworkWindow(graph);

            window.setVisible(true);
        });
    }


    private static class NetworkPanel extends JPanel {

        private static final int NODE_RADIUS = 28;

        private static final int ARROW_SIZE = 12;

        private final TrainGraph graph;

        private final Map<String, Point2D.Double>
                stationPositions = new HashMap<>();

        private final Map<String, Integer>
                displayOrder = new LinkedHashMap<>();

        private Station selectedStation;

        /*
         * Used to prevent price/duration labels
         * from overlapping each other.
         */
        private final List<Rectangle>
                usedLabelAreas = new ArrayList<>();


        public NetworkPanel(TrainGraph graph) {

            this.graph = graph;

            setBackground(Color.WHITE);

            setPreferredSize(
                    new Dimension(
                            1450,
                            1250
                    )
            );

            createStationPositions();

            createDisplayOrder();


            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseClicked(
                                MouseEvent e
                        ) {

                            Station clickedStation =
                                    findStationAt(
                                            e.getPoint()
                                    );

                            if (clickedStation != null) {

                                selectedStation =
                                        clickedStation;

                            } else {

                                /*
                                 * Click empty space
                                 * to remove selection.
                                 */
                                selectedStation = null;
                            }

                            repaint();
                        }
                    }
            );
        }

        // station position
        private void createStationPositions() {

            stationPositions.put(
                    "PAD",
                    new Point2D.Double(
                            100,
                            100
                    )
            );

            stationPositions.put(
                    "ARA",
                    new Point2D.Double(
                            350,
                            100
                    )
            );

            stationPositions.put(
                    "ALS",
                    new Point2D.Double(
                            600,
                            100
                    )
            );

            stationPositions.put(
                    "SPG",
                    new Point2D.Double(
                            850,
                            100
                    )
            );

            stationPositions.put(
                    "TGS",
                    new Point2D.Double(
                            1100,
                            100
                    )
            );


            stationPositions.put(
                    "BUT",
                    new Point2D.Double(
                            320,
                            310
                    )
            );

            stationPositions.put(
                    "BM",
                    new Point2D.Double(
                            650,
                            310
                    )
            );

            stationPositions.put(
                    "TAS",
                    new Point2D.Double(
                            950,
                            310
                    )
            );

            stationPositions.put(
                    "IPH",
                    new Point2D.Double(
                            1280,
                            390
                    )
            );


            stationPositions.put(
                    "BDR",
                    new Point2D.Double(
                            650,
                            570
                    )
            );

            stationPositions.put(
                    "KKB",
                    new Point2D.Double(
                            1030,
                            570
                    )
            );


            stationPositions.put(
                    "KLS",
                    new Point2D.Double(
                            830,
                            760
                    )
            );


            stationPositions.put(
                    "SRM",
                    new Point2D.Double(
                            830,
                            900
                    )
            );


            stationPositions.put(
                    "GMS",
                    new Point2D.Double(
                            600,
                            1080
                    )
            );

            stationPositions.put(
                    "SEG",
                    new Point2D.Double(
                            800,
                            1080
                    )
            );

            stationPositions.put(
                    "KLV",
                    new Point2D.Double(
                            1030,
                            1080
                    )
            );

            stationPositions.put(
                    "JHB",
                    new Point2D.Double(
                            1280,
                            1080
                    )
            );
        }

        // display order
        private void createDisplayOrder() {

            String[] order = {

                    "PAD",
                    "ARA",
                    "ALS",
                    "SPG",
                    "TGS",

                    "BUT",
                    "BM",
                    "BDR",
                    "TAS",
                    "IPH",

                    "KKB",
                    "KLS",
                    "SRM",

                    "GMS",
                    "SEG",
                    "KLV",
                    "JHB"
            };


            for (int i = 0; i < order.length; i++) {

                displayOrder.put(
                        order[i],
                        i
                );
            }
        }

        // paint
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            /*
             * Clear old label positions
             * every time graph is repainted.
             */
            usedLabelAreas.clear();


            drawNetwork(g2);

            drawStations(g2);

            g2.dispose();
        }

        // draw network
         private void drawNetwork(Graphics2D g2) {

            Set<String> highlightedPairs =
                    getHighlightedPairs();

            Set<String> drawnPairs =
                    new HashSet<>();

            // draw normal routes
            for (Station source :
                    graph.getVertices()) {

                for (TrainEdge<Station> edge :
                        graph.getEdges(source)) {

                    Station destination =
                            edge.getDestination();


                    String pairKey =
                            createPairKey(
                                    source,
                                    destination
                            );


                    /*
                     * Avoid duplicate line.
                     *
                     * Example:
                     *
                     * PAD -> ARA
                     * ARA -> PAD
                     *
                     * only one line is drawn.
                     */
                    if (drawnPairs.contains(pairKey)) {

                        continue;
                    }


                    drawnPairs.add(pairKey);


                    /*
                     * If this route belongs to
                     * selected station, skip
                     * grey version.
                     *
                     * It will be drawn blue later.
                     */
                    if (highlightedPairs.contains(pairKey)) {

                        continue;
                    }


                    Station displaySource =
                            source;

                    Station displayDestination =
                            destination;


                    boolean reverseExists =
                            graph.getEdge(
                                    destination,
                                    source
                            ) != null;


                    /*
                     * If both directions exist,
                     * choose only ONE arrow direction
                     * for the normal graph.
                     */
                    if (reverseExists) {

                        if (compareDisplayOrder(
                                source,
                                destination
                        ) > 0) {

                            displaySource =
                                    destination;

                            displayDestination =
                                    source;
                        }
                    }


                    drawRoute(
                            g2,

                            displaySource,

                            displayDestination,

                            null,

                            new Color(
                                    185,
                                    185,
                                    185
                            ),

                            1.5f,

                            false,

                            0
                    );
                }
            }

            // draw selected routes
             if (selectedStation != null) {

                int routeIndex = 0;


                for (TrainEdge<Station> edge :
                        graph.getEdges(
                                selectedStation
                        )) {

                    Station destination =
                            edge.getDestination();


                    /*
                     * Selected station becomes
                     * the source.
                     *
                     * Arrow:
                     *
                     * selected station
                     *       ↓
                     * destination
                     */
                    drawRoute(
                            g2,

                            selectedStation,

                            destination,

                            edge,

                            new Color(
                                    37,
                                    91,
                                    168
                            ),

                            3.5f,

                            true,

                            routeIndex
                    );


                    routeIndex++;
                }
            }
        }

        // get highlighted route
        private Set<String> getHighlightedPairs() {

            Set<String> highlightedPairs =
                    new HashSet<>();


            if (selectedStation == null) {

                return highlightedPairs;
            }


            for (TrainEdge<Station> edge :
                    graph.getEdges(
                            selectedStation
                    )) {

                highlightedPairs.add(

                        createPairKey(
                                selectedStation,
                                edge.getDestination()
                        )
                );
            }


            return highlightedPairs;
        }

        // create unique pair key
        private String createPairKey(
                Station first,
                Station second
        ) {

            String firstCode =
                    first.getStationCode();

            String secondCode =
                    second.getStationCode();


            if (firstCode.compareTo(
                    secondCode
            ) < 0) {

                return firstCode
                        + "-"
                        + secondCode;
            }


            return secondCode
                    + "-"
                    + firstCode;
        }

        // compare station display order
         private int compareDisplayOrder(
                Station first,
                Station second
        ) {

            int firstOrder =
                    displayOrder.getOrDefault(
                            first.getStationCode(),
                            Integer.MAX_VALUE
                    );


            int secondOrder =
                    displayOrder.getOrDefault(
                            second.getStationCode(),
                            Integer.MAX_VALUE
                    );


            return Integer.compare(
                    firstOrder,
                    secondOrder
            );
        }

        // draw one route
        private void drawRoute(
                Graphics2D g2,
                Station source,
                Station destination,
                TrainEdge<Station> edge,
                Color color,
                float strokeWidth,
                boolean showWeight,
                int routeIndex
        ) {

            Point sourcePoint =
                    getStationPoint(source);

            Point destinationPoint =
                    getStationPoint(destination);


            double dx =
                    destinationPoint.x
                            -
                            sourcePoint.x;


            double dy =
                    destinationPoint.y
                            -
                            sourcePoint.y;


            double length =
                    Math.sqrt(
                            dx * dx
                                    +
                                    dy * dy
                    );


            if (length == 0) {

                return;
            }


            double unitX =
                    dx / length;

            double unitY =
                    dy / length;

            // start outside source circle
            int startX =
                    (int) (
                            sourcePoint.x
                                    +
                                    unitX * NODE_RADIUS
                    );


            int startY =
                    (int) (
                            sourcePoint.y
                                    +
                                    unitY * NODE_RADIUS
                    );

            // stop before destination circle
             int endX =
                    (int) (
                            destinationPoint.x
                                    -
                                    unitX * NODE_RADIUS
                    );


            int endY =
                    (int) (
                            destinationPoint.y
                                    -
                                    unitY * NODE_RADIUS
                    );

            // draw line
             g2.setColor(color);


               g2.setStroke(
                    new BasicStroke(
                            strokeWidth,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawLine(
                    startX,
                    startY,
                    endX,
                    endY
            );

            // one arrow
           drawArrow(
                    g2,

                    startX,
                    startY,

                    endX,
                    endY,

                    color
            );

            // show price and duration for selected route
            if (showWeight && edge != null) {

                drawWeightLabel(
                        g2,

                        edge,

                        startX,
                        startY,

                        endX,
                        endY,

                        routeIndex
                );
            }
        }

        // Draw arrow
       private void drawArrow(
                Graphics2D g2,
                int startX,
                int startY,
                int endX,
                int endY,
                Color color
        ) {

            double dx =
                    endX - startX;

            double dy =
                    endY - startY;


            double angle =
                    Math.atan2(
                            dy,
                            dx
                    );


            /*
             * Arrow slightly closer
             * to destination.
             */
            double arrowPosition =
                    0.68;


            double arrowX =
                    startX
                            +
                            dx * arrowPosition;


            double arrowY =
                    startY
                            +
                            dy * arrowPosition;


            int tipX =
                    (int) arrowX;

            int tipY =
                    (int) arrowY;


            int leftX =
                    (int) (
                            arrowX
                                    -
                                    ARROW_SIZE
                                            *
                                            Math.cos(
                                                    angle
                                                            -
                                                            Math.PI / 6
                                            )
                    );


            int leftY =
                    (int) (
                            arrowY
                                    -
                                    ARROW_SIZE
                                            *
                                            Math.sin(
                                                    angle
                                                            -
                                                            Math.PI / 6
                                            )
                    );


            int rightX =
                    (int) (
                            arrowX
                                    -
                                    ARROW_SIZE
                                            *
                                            Math.cos(
                                                    angle
                                                            +
                                                            Math.PI / 6
                                            )
                    );


            int rightY =
                    (int) (
                            arrowY
                                    -
                                    ARROW_SIZE
                                            *
                                            Math.sin(
                                                    angle
                                                            +
                                                            Math.PI / 6
                                            )
                    );


            Polygon arrowHead =
                    new Polygon();


            arrowHead.addPoint(
                    tipX,
                    tipY
            );

            arrowHead.addPoint(
                    leftX,
                    leftY
            );

            arrowHead.addPoint(
                    rightX,
                    rightY
            );


            g2.setColor(color);

            g2.fillPolygon(
                    arrowHead
            );
        }

        // Draw price and duration
       private void drawWeightLabel(
                Graphics2D g2,
                TrainEdge<Station> edge,
                int startX,
                int startY,
                int endX,
                int endY,
                int routeIndex
        ) {

            /*
             * Weightage from dummy data.
             *
             * Example:
             *
             * 38 min | RM8.00
             */
            String weightText =
                    edge.getDuration()
                            +
                            " min | RM"
                            +
                            String.format(
                                    "%.2f",
                                    edge.getPrice()
                            );


            double dx =
                    endX - startX;

            double dy =
                    endY - startY;


            double length =
                    Math.sqrt(
                            dx * dx
                                    +
                                    dy * dy
                    );


            if (length == 0) {

                return;
            }


            /*
             * Perpendicular direction.
             *
             * This allows the label to move
             * away from the actual line.
             */
            double perpendicularX =
                    -dy / length;

            double perpendicularY =
                    dx / length;


            /*
             * Different routes use slightly
             * different positions.
             *
             * This helps reduce label overlap.
             */
            double[] positions = {
                    0.35,
                    0.45,
                    0.55,
                    0.40,
                    0.60,
                    0.50
            };


            double position =
                    positions[
                            routeIndex
                                    %
                                    positions.length
                            ];


            double baseX =
                    startX
                            +
                            dx * position;


            double baseY =
                    startY
                            +
                            dy * position;


            /*
             * Alternate label side:
             *
             * first  = one side
             * second = other side
             */
            int side;


            if (routeIndex % 2 == 0) {

                side = 1;

            } else {

                side = -1;
            }


            int offset =
                    22;


            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );


            FontMetrics fm =
                    g2.getFontMetrics();


            int textWidth =
                    fm.stringWidth(
                            weightText
                    );


            int textHeight =
                    fm.getHeight();


            Rectangle labelArea = null;

            int labelCenterX = 0;
            int labelCenterY = 0;


            /*
             * Try several positions until
             * the label doesn't overlap
             * another price/duration label.
             */
            for (
                    int attempt = 0;
                    attempt < 8;
                    attempt++
            ) {

                int currentOffset =
                        offset
                                +
                                attempt * 18;


                labelCenterX =
                        (int) (
                                baseX
                                        +
                                        perpendicularX
                                                *
                                                currentOffset
                                                *
                                                side
                        );


                labelCenterY =
                        (int) (
                                baseY
                                        +
                                        perpendicularY
                                                *
                                                currentOffset
                                                *
                                                side
                        );


                int boxX =
                        labelCenterX
                                -
                                textWidth / 2
                                -
                                7;


                int boxY =
                        labelCenterY
                                -
                                textHeight / 2
                                -
                                4;


                int boxWidth =
                        textWidth + 14;


                int boxHeight =
                        textHeight + 8;


                Rectangle testArea =
                        new Rectangle(
                                boxX,
                                boxY,
                                boxWidth,
                                boxHeight
                        );


                if (!isLabelOverlapping(testArea)) {

                    labelArea =
                            testArea;

                    break;
                }
            }


            /*
             * Fallback position.
             */
            if (labelArea == null) {

                labelArea =
                        new Rectangle(

                                labelCenterX
                                        -
                                        textWidth / 2
                                        -
                                        7,

                                labelCenterY
                                        -
                                        textHeight / 2
                                        -
                                        4,

                                textWidth + 14,

                                textHeight + 8
                        );
            }


            usedLabelAreas.add(
                    labelArea
            );

            // background = white
            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            245
                    )
            );


            g2.fillRoundRect(

                    labelArea.x,

                    labelArea.y,

                    labelArea.width,

                    labelArea.height,

                    10,

                    10
            );

            // Border
             g2.setColor(
                    new Color(
                            37,
                            91,
                            168
                    )
            );


            g2.setStroke(
                    new BasicStroke(
                            1.2f
                    )
            );


            g2.drawRoundRect(

                    labelArea.x,

                    labelArea.y,

                    labelArea.width,

                    labelArea.height,

                    10,

                    10
            );

            // Label text
             g2.setColor(
                    new Color(
                            30,
                            30,
                            30
                    )
            );


            int textX =
                    labelArea.x
                            +
                            7;


            int textY =
                    labelArea.y
                            +
                            (
                                    labelArea.height
                                            -
                                            fm.getHeight()
                            ) / 2
                            +
                            fm.getAscent();


            g2.drawString(
                    weightText,
                    textX,
                    textY
            );
        }

        // Check label overlap
         private boolean isLabelOverlapping(
                Rectangle newArea
        ) {

            /*
             * Add some extra space between
             * labels.
             */
            Rectangle expandedArea =
                    new Rectangle(

                            newArea.x - 5,

                            newArea.y - 5,

                            newArea.width + 10,

                            newArea.height + 10
                    );


            for (Rectangle existingArea :
                    usedLabelAreas) {

                if (expandedArea.intersects(
                        existingArea
                )) {

                    return true;
                }
            }


            return false;
        }

        // Draw stations
        private void drawStations(
                Graphics2D g2
        ) {

            for (Station station :
                    graph.getVertices()) {

                Point point =
                        getStationPoint(
                                station
                        );


                /*
                 * Selected station = orange
                 */
                if (station.equals(
                        selectedStation
                )) {

                    g2.setColor(
                            new Color(
                                    235,
                                    120,
                                    20
                            )
                    );

                } else {

                    /*
                     * Normal station = blue
                     */
                    g2.setColor(
                            new Color(
                                    37,
                                    91,
                                    168
                            )
                    );
                }



                // Station circle
                g2.fillOval(

                        point.x
                                -
                                NODE_RADIUS,

                        point.y
                                -
                                NODE_RADIUS,

                        NODE_RADIUS * 2,

                        NODE_RADIUS * 2
                );

                // Station code
               g2.setColor(
                        Color.WHITE
                );


                g2.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );


                String stationCode =
                        station.getStationCode();


                FontMetrics codeMetrics =
                        g2.getFontMetrics();


                int codeX =
                        point.x
                                -
                                codeMetrics.stringWidth(
                                        stationCode
                                ) / 2;


                int codeY =
                        point.y
                                +
                                codeMetrics.getAscent()
                                        / 2
                                -
                                2;


                g2.drawString(
                        stationCode,
                        codeX,
                        codeY
                );

                // Station name
                g2.setColor(
                        Color.BLACK
                );


                g2.setFont(
                        new Font(
                                "Arial",
                                Font.PLAIN,
                                12
                        )
                );


                String stationName =
                        station.getStationName();


                FontMetrics nameMetrics =
                        g2.getFontMetrics();


                int nameX =
                        point.x
                                -
                                nameMetrics.stringWidth(
                                        stationName
                                ) / 2;


                int nameY =
                        point.y
                                +
                                NODE_RADIUS
                                +
                                20;


                g2.drawString(
                        stationName,
                        nameX,
                        nameY
                );
            }
        }
        // Get station position
        private Point getStationPoint(
                Station station
        ) {

            Point2D.Double position =
                    stationPositions.get(
                            station.getStationCode()
                    );


            if (position != null) {

                return new Point(
                        (int) position.x,
                        (int) position.y
                );
            }


            return new Point(
                    100,
                    100
            );
        }

        // find clicked station
         private Station findStationAt(
                Point mousePoint
        ) {

            for (Station station :
                    graph.getVertices()) {

                Point stationPoint =
                        getStationPoint(
                                station
                        );


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
    }
}