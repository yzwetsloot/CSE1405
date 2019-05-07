package assignment1;

public class CyclicRedundancyCheck {
    /**
     * Calculates the CRC check value, or -1 if it cannot be calculated.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence
     * @return The CRC check value
     */
    public static long calculateCRC(long bitSequence, int inputLength, long generatorSequence) {
        long generatorLength = (long) (Math.floor(Math.log(generatorSequence) / Math.log(2)) + 1);
        System.out.println(generatorLength);
        if((inputLength > generatorLength) && (generatorLength > 1)) {
            long mask = 0;
            mask |= 1L << generatorLength - 1;
            mask |= 1L;
            System.out.println(Long.toBinaryString(mask));
            boolean validGeneratorSequence = (Long.bitCount(generatorSequence & mask) == 2);
            if(!validGeneratorSequence) {
                return -1;
            }
            long crc = 0;
            long message = bitSequence << generatorLength - 1;
            System.out.println(Long.toBinaryString(message));
            return crc;
        }
        else {
            return -1;
        }
    }

    /**
     * Checks the correctness of the bit sequence.
     *
     * @param bitSequence The CRC bit sequence including the CRC check value
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence used
     * @param checkSequence The CRC check value to check against
     * @return true if the sequence is correct, false otherwise
     */
    public static boolean checkCRC(long bitSequence, int inputLength, long generatorSequence, long checkSequence) {
        return false;
        // TODO
    }

    public static void main(String[] args) {
        long bitSequence = Long.parseLong("11010011101100", 2);
        long generatorSequence = Long.parseLong("1011", 2);
        long falseGeneratorSequence = Long.parseLong("10001011", 2);
        System.out.println(calculateCRC(bitSequence, 14, generatorSequence));
        System.out.println(calculateCRC(bitSequence, 14, falseGeneratorSequence));
    }
}
