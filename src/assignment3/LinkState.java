package assignment3;

import java.util.*;

public class LinkState {

    static final String DATA_PACKET_TYPE = "D";

    static final String ERROR_INVALID_TYPE = "INVALID TYPE";

    static final String ERROR_NO_ROUTE = "NO ROUTE";

    static final String MESSAGE_THANK_YOU = "THANK YOU";

    static final String MESSAGE_ROUTE_RECEIVED = "RECEIVED";

    static final String ROUTING_PACKET_TYPE = "R";

    public String routerIdentifier;

    public Map<String, LSRoute> table;

    public Map<String, Integer> neighbourRouters;

    /**
     * Constructor to take in the router's identifier.
     *
     * @param routerIdentifier Identifier of the router.
     */
    public LinkState(String routerIdentifier, Map<String, Integer> neighbourRouters) {
        this.routerIdentifier = routerIdentifier;
        this.neighbourRouters = neighbourRouters;
    }

    /**
     * GIVEN: This processes the packets for you and calls the routing or data packet processors accordingly.
     *
     * @param packet Network packet supplied by our test cases.
     * @return The reply of the router.
     */
    public String processPacket(String packet) {
        String[] packetParts = packet.split(" ");
        String packetType = packetParts[0];
        // Detect the type of message
        switch(packetType) {
            case ROUTING_PACKET_TYPE:
                int numberOfRoutes = Integer.parseInt(packetParts[2]);
                ArrayList<LSRoute> routes = new ArrayList<>();
                // Extract routingTable from message
                int index = 3;
                for (int i = 0; i < numberOfRoutes; i++) {
                    routes.add(new LSRoute(packetParts[index], Integer.parseInt(packetParts[index + 1])));
                    index += 2;
                }
                return processRoutingPacket(packetParts[1], routes);
            case DATA_PACKET_TYPE:
                return processDataPacket(packetParts[1], packetParts[2], packetParts[3]);
            default:
                return ERROR_INVALID_TYPE;
        }
    }

    /**
     * Process the data packet, this function is called automatically from processPacket.
     *
     * @param source      Source of message.
     * @param destination Destination of message.
     * @param message     Arbitrary message.
     * @return The neighbour router which can route to the desired destination and the total latency
     * to the final destination. If no valid route can be found the function should return ERROR_NO_ROUTE,
     * which is mapped to 'NO ROUTE' in the code. If a message is directed to the current router,
     * the router should return MESSAGE_THANK_YOU, which is mapped to 'THANK YOU' in the code.
     */
    private String processDataPacket(String source, String destination, String message) {
        if (!destination.equals(this.routerIdentifier)) {
            if (table.containsKey(destination)) {
                return "";
            }
            return ERROR_NO_ROUTE;
        }
        return MESSAGE_THANK_YOU;
    }

    /**
     * Process the routing packet, this function is called automatically from processPacket.
     *
     * @param routerIdentifier Neighbour router identifier.
     * @param routes           The neighbours of the neighbour router given as a list of Route objects.
     * @return MESSAGE_ROUTE_RECEIVED (which is mapped to 'RECEIVED' in the code).
     */
    private String processRoutingPacket(String routerIdentifier, List<LSRoute> routes) {
        if (!neighbourRouters.containsKey(routerIdentifier)) {
            for (LSRoute i : routes) {
                if (i.routerIdentifier.equals(routerIdentifier)) {
                    table.put(routerIdentifier, new LSRoute(routerIdentifier, i.latency));
                }
            }
        }
        return MESSAGE_ROUTE_RECEIVED;
    }

    public static void main(String[] args) {
        Map<String, Integer> neighbours = new HashMap<>();
        neighbours.put("1744", 197);
        neighbours.put("1920", 11);
        LinkState rt = new LinkState("aaaa", neighbours);
        System.out.println(LinkState.MESSAGE_ROUTE_RECEIVED.equals(rt.processPacket("R 1744 3 aaaa 197 1920 39 024a 12")));
        System.out.println(LinkState.MESSAGE_ROUTE_RECEIVED.equals(rt.processPacket("R 1920 3 aaaa 11 1744 39 024a 198")));
        System.out.println(LinkState.MESSAGE_ROUTE_RECEIVED.equals(rt.processPacket("R 024a 2 1744 12 1920 198")));
        System.out.println("1920 62".equals(rt.processPacket("D cccc 024a FEDCBA")));
    }
}

/**
 * GIVEN: Route entry used for storing route information.
 * This is used for representing the neighbours from the neighbouring router.
 */
class LSRoute {

    String routerIdentifier;

    int latency;

    /**
     * GIVEN: Constructor for a route.
     *
     * @param routerIdentifier Identifier of router.
     * @param latency          Latency from neighbouring router.
     */
    public LSRoute(String routerIdentifier, int latency) {
        this.routerIdentifier = routerIdentifier;
        this.latency = latency;
    }
}

/**
 * GIVEN: Graph used to represent the network with nodes and edges.
 */
class NetworkGraph {

    private Map<String, Router> nodes;

    private Map<Router, NetworkRoute> routingTable;

    /**
     * GIVEN: Constructor for the network graph.
     */
    NetworkGraph() {
        this.nodes = new HashMap<>();
        this.routingTable = new HashMap<>();
    }

    /**
     * GIVEN: Add a router node to the network graph.
     *
     * @param router The router node to be added.
     */
    void addRouter(Router router) {
        this.nodes.put(router.identifier, router);
    }

    /**
     * GIVEN: Retrieve a router node from the network graph.
     *
     * @param routerIdentifier The identifier of the router node to retrieve.
     * @return The router node requested or null if not found.
     */
    Router getRouter(String routerIdentifier) {
        return this.nodes.getOrDefault(routerIdentifier, null);
    }

    /**
     * GIVEN: Checks if the routing table contains a route from the current router to the provided router.
     *
     * @param routerIdentifier The identifier of the router to check for.
     * @return True if a route exists from the current router to the provided router, false otherwise.
     */
    boolean hasRouteToRouter(String routerIdentifier) {
        Router router = this.getRouter(routerIdentifier);
        if (router == null) {
            return false;
        }
        return routingTable.containsKey(router);
    }

    /**
     * GIVEN: Recomputes the latencies to each node from the provided router using Dijkstra's algorithm.
     *
     * @param home The origin router node
     */
    void recomputeCosts(Router home) {
        // Clear routing table and distances
        this.routingTable.clear();
        PriorityQueue<Router> queue = new PriorityQueue<>();
        for (Router r : this.nodes.values()) {
            r.resetLatency();
        }
        // Set origin point
        home.latency = 0;
        home.previous = home;
        queue.add(home);
        // Calculate minimum distances
        while (!queue.isEmpty()) {
            // Get next router
            Router nextRouter = queue.poll();
            // Add router to the routing table
            this.routingTable.put(nextRouter, new NetworkRoute(nextRouter.previous, nextRouter.latency));
            // Check the distances to neighbouring router notes
            for (String neighbourIdentifier : nextRouter.neighbours.keySet()) {
                Router neighbourRouter = this.nodes.get(neighbourIdentifier);
                // newDistance = latency from current router to next router + latency from next router to its neighbour
                int newDistance = nextRouter.latency + nextRouter.getLatencyToNeighbour(neighbourIdentifier);
                if (newDistance < neighbourRouter.latency) {
                    // Update distances and requeue
                    queue.remove(neighbourRouter);
                    neighbourRouter.latency = newDistance;
                    neighbourRouter.previous = nextRouter;
                    queue.add(neighbourRouter);
                }
            }
        }
    }
}

/**
 * GIVEN: Network route used for storing route information in the routing table
 */
class NetworkRoute implements Comparable<NetworkRoute> {

    private Router router;

    private int latency;

    NetworkRoute(Router router, int latency) {
        this.router = router;
        this.latency = latency;
    }

    public int compareTo(NetworkRoute networkRoute) {
        if (this.latency - networkRoute.latency == 0) {
            return this.router.identifier.compareTo(networkRoute.router.identifier);
        }
        return this.latency - networkRoute.latency;
    }
}

/**
 * GIVEN: Router class representing a router node in the network graph.
 */
class Router implements Comparable<Router> {

    String identifier;

    Map<String, Integer> neighbours;

    Router previous;

    int latency;

    /**
     * GIVEN: Constructor for the router node.
     *
     * @param identifier The identifier of the router node.
     */
    Router(String identifier) {
        this.identifier = identifier;
        this.neighbours = new HashMap<>();
        this.previous = null;
        this.resetLatency();
    }

    /**
     * GIVEN: Retrieve the latency to the provided neighbour.
     *
     * @param neighbourIdentifier The identifier of the neighbour.
     * @return The latency to the neighbour or MAX_INT / 2 if not found.
     */
    int getLatencyToNeighbour(String neighbourIdentifier) {
        if (this.neighbours.containsKey(neighbourIdentifier)) {
            return this.neighbours.get(neighbourIdentifier);
        }
        return Integer.MAX_VALUE / 2;
    }

    /**
     * GIVEN: Set the latency for a neighbour router node.
     *
     * @param neighbourIdentifier The identifier of the neighbour router node.
     * @param latency The latency to the neighbour router node.
     */
    void setLatencyToNeighbour(String neighbourIdentifier, int latency) {
        this.neighbours.put(neighbourIdentifier, latency);
    }

    /**
     * GIVEN: Reset latency to origin
     */
    void resetLatency() {
        this.latency = Integer.MAX_VALUE / 2;
        this.previous = null;
    }

    /**
     * GIVEN: Reset latency to origin and clear neighbours
     */
    void clear() {
        this.resetLatency();
        this.neighbours.clear();
    }

    /**
     * GIVEN: Retrieve the forwarding router to the provided router node.
     *
     * @param routerIdentifier The identifier for the router node.
     * @return The router node that can forward the message to the provided router node
     */
    Router getForwardingRouter(Router routerIdentifier) {
        Router finger = this;
        while (finger.previous != routerIdentifier) {
            finger = finger.previous;
        }
        return finger;
    }

    @Override
    public int compareTo(Router router) {
        if (this.latency - router.latency == 0) {
            return this.identifier.compareTo(router.identifier);
        }
        return this.latency - router.latency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Router router = (Router) o;
        return Objects.equals(identifier, router.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
