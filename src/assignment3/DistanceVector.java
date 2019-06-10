package assignment3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Routing table for dynamic distance vector routing.
 * It holds a list of routes that can be queried to find the router which can reach the destination.
 */
class DistanceVector {
    public static final String DATA_PACKET_TYPE = "D";

    public static final String ERROR_INVALID_TYPE = "INVALID TYPE";

    public static final String ERROR_NO_ROUTE = "NO ROUTE";

    public static final String MESSAGE_THANK_YOU = "THANK YOU";

    public static final String MESSAGE_ROUTE_RECEIVED = "RECEIVED";

    public static final String ROUTING_PACKET_TYPE = "R";

    public String routerIdentifier;

    public Map<String, Route> table;

    /**
     * Constructor to take in the router's identifier.
     *
     * @param routerIdentifier Identifier of the router
     */
    public DistanceVector(String routerIdentifier) {
        this.routerIdentifier = routerIdentifier;
        table = new HashMap<>();
    }

    /**
     * GIVEN: This processes the packets for you and calls the routing or data packet processors accordingly.
     *
     * @param packet Network packet supplied by our test cases.
     * @return String of the reply from the incoming packet.
     */
    public String processPacket(String packet) {
        String[] packetParts = packet.split(" ");
        String packetType = packetParts[0];
        switch(packetType) {
            case ROUTING_PACKET_TYPE:
                int numberOfRoutes = Integer.parseInt(packetParts[3]);
                ArrayList<Route> routes = new ArrayList<>();
                int index = 4;
                for (int i = 0; i < numberOfRoutes; i++) {
                    routes.add(new Route(packetParts[index], Integer.parseInt(packetParts[index + 1])));
                    index += 2;
                }
                return processRoutingPacket(packetParts[1], Integer.parseInt(packetParts[2]), routes);
            case DATA_PACKET_TYPE:
                return processDataPacket(packetParts[1], packetParts[2], packetParts[3]);
            default:
                return ERROR_INVALID_TYPE;
        }
    }

    /**
     * Process the data packet, this function is called automatically from processPacket.
     *
     * @param source      Original source of message.
     * @param destination Final destination of message.
     * @param message     Fake message.
     * @return The neighbour router which can route to the desired destination and the total latency
     * to the final destination. If no valid route can be found the function should return ERROR_NO_ROUTE,
     * which is mapped to 'NO ROUTE' in the code. If a message is directed to the current router,
     * the router should return MESSAGE_THANK_YOU, which is mapped to 'THANK YOU' in the code.
     */
    private String processDataPacket(String source, String destination, String message) {
        if (!destination.equals(this.routerIdentifier)) {
            if (table.containsKey(destination)) {
                int lowestLatency = Integer.MAX_VALUE;
                Route closest = null;
                for (Map.Entry<String, Route> entry : table.entrySet()) {
                    if (entry.getKey().equals(destination) &&
                            entry.getValue().latency < lowestLatency) {
                        closest = entry.getValue();
                    }
                }
                String ret = closest.forwardingRouter + " " + closest.latency;
                System.out.println(ret);
                return ret;
            }
            return ERROR_NO_ROUTE;
        }
        return MESSAGE_THANK_YOU;
    }

    /**
     * Process the router packet, this function is called automatically from processPacket.
     *
     * @param routerIdentifier Originating router id given in hexadecimal.
     * @param latency          Latency from the origin router.
     * @param routes           The neighbours of the origin router given as a list of Route objects.
     * @return MESSAGE_ROUTE_RECEIVED (which is mapped to 'RECEIVED' in the code).
     */
    private String processRoutingPacket(String routerIdentifier, int latency, List<Route> routes) {
        if (!table.containsKey(routerIdentifier)) {
            table.put(routerIdentifier, new Route(routerIdentifier, latency));
        }
        for (Route neighborRoute : routes) {
            if (table.containsKey(neighborRoute.forwardingRouter) &&
                    table.get(neighborRoute.forwardingRouter).forwardingRouter.equals(routerIdentifier)) {
                table.get(neighborRoute.forwardingRouter).latency += latency;
            } else {
                table.put(neighborRoute.forwardingRouter, new Route(routerIdentifier, latency + neighborRoute.latency));
            }
        }
        return MESSAGE_ROUTE_RECEIVED;
    }

    public static void main(String[] args) {
        DistanceVector distanceVector = new DistanceVector("004b");
        System.out.println(DistanceVector.MESSAGE_ROUTE_RECEIVED.equals(distanceVector.processPacket("R 1530 80 1 72ba 10")));
        System.out.println("1530 90".equals(distanceVector.processPacket("D b974 72ba eeb7c117b3b8821")));
        System.out.println(DistanceVector.MESSAGE_THANK_YOU.equals(distanceVector.processPacket("D b974 004b eeb7c117b3b8821")) + "\n");
        distanceVector.table.forEach((key, value) -> System.out.println(key + " " + value.forwardingRouter + " " + value.latency));
    }
}

/**
 * GIVEN: Route entry used for storing route information
 * This is used for representing the forwarding routes from the neighbouring router
 */
class Route {

    String forwardingRouter;

    int latency;

    public Route(String forwardingRouterIdentifier, int latency) {
        this.forwardingRouter = forwardingRouterIdentifier;
        this.latency = latency;
    }
}
