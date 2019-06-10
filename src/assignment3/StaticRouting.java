package assignment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Routing table for static routing.
 * It holds a list of static routes that can be queried to find the gateway which can reach the destination.
 */
class StaticRouting {

    public static int ERROR_NO_ROUTE = -1;

    List<int[]> routingTable = new ArrayList<>();

    /**
     * Adds a route to the routing table.
     *
     * @param networkPrefix Network prefix
     * @param subnetMask    Subnet mask
     * @param gateway       Gateway
     */
    public void addRoute(int networkPrefix, int subnetMask, int gateway) {
        int[] temp = {networkPrefix, subnetMask, gateway};
        routingTable.add(temp);
    }

    /**
     * Queries the routing table to determine which gateway can reach the desired network the IP address belongs to.
     *
     * @param address The IP address we want to route to.
     * @return The gateway that can reach the desired network, or ERROR_NO_ROUTE when no route to that network can be found.
     */
    public int lookupRoute(int address) {
        List<int[]> matchList = new ArrayList<>();
        for (int[] entry : routingTable) {
            if (entry[0] == (address & entry[1])) {
                matchList.add(entry);
            }
        }
        if (!matchList.isEmpty()) {
            int longest = 0;
            int bitCount = 0;
            for (int[] matchingEntry : matchList) {
                if (Integer.bitCount(matchingEntry[1]) >= bitCount) {
                    longest = matchingEntry[2];
                    bitCount = Integer.bitCount(matchingEntry[1]);
                }
            }
            return longest;
        }
        return ERROR_NO_ROUTE;
    }
}
