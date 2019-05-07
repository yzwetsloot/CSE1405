package assignment1;

import java.util.ArrayList;
import java.util.List;

public class HammingDistance {
    /**
     * Calculates the hamming distance of the given code, or returns -1 if it cannot be calculated.
     *
     * @param code The code
     * @return The hamming distance of the given code , or -1 if it cannot be calculated
     */
    public static long calculate(List<Long> code) {
        if(code.size() > 1) {
            long hammingDistance = Long.MAX_VALUE;
            for(int i = 0; i < code.size(); i++) {
                for(int j = 0; j < code.size(); j++) {
                    if(!code.get(i).equals(code.get(j))) {
                        long oneCount = Long.bitCount(code.get(i) ^ code.get(j));
                        if(oneCount < hammingDistance) {
                            hammingDistance = oneCount;
                        }
                    }
                }
            }
            return hammingDistance;
        }
        else {
            return -1;
        }
    }

    public static void main(String[] args) {
        ArrayList<Long> list = new ArrayList<>();
        list.add(Long.parseLong("00000000", 2));
        list.add(Long.parseLong("00001111", 2));
        list.add(Long.parseLong("11110000", 2));
        list.add(Long.parseLong("11111111", 2));

        System.out.println(calculate(list));
    }
}
