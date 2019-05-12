package assignment1;

public class CyclicRedundancyCheck {

    /**
     * Computes length of a given bitSequence starting from leading 1.
     *
     * @param bitSequence The input bit sequence as a long.
     * @return length of bitSequence
     */
    private static long length(long bitSequence) {
        return (long) (Math.floor(Math.log(bitSequence) / Math.log(2)) + 1);
    }

    /**
     * Calculates the CRC check value, or -1 if it cannot be calculated.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence
     * @return The CRC check value
     */
    public static long calculateCRC(long bitSequence, int inputLength, long generatorSequence) {
        long generatorLength = length(generatorSequence);
        if ((inputLength >= generatorLength) && (generatorLength > 1)) {
            long mask = 0;
            mask |= 1L << generatorLength - 1;
            mask |= 1L;
            boolean validGeneratorSequence = (Long.bitCount(generatorSequence & mask) == 2);
            if (!validGeneratorSequence) {
                return -1;
            }
            long message = bitSequence << generatorLength - 1;
            return division(inputLength, generatorSequence, message);
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
        return calculateCRC(bitSequence, inputLength, generatorSequence) == checkSequence;
    }

    /**
     * Performs division on given message and generator sequence bit sequences.
     *
     * @param inputLength Length of given input sequence
     * @param generatorSequence Divisor of the division
     * @param message Variable length message as result of division, returned as remainder
     * @return remainder of the division
     */
    private static long division(int inputLength, long generatorSequence, long message) {
        long temp = generatorSequence << (inputLength - 1);
        for (int i = 0; i < inputLength; i++) {
            if (length(message) == length(temp)) {
                message = message ^ temp;
                temp = temp >> 1L;
            }
            else {
                temp = temp >> 1L;
                continue;
            }
        }
        return message;
    }
}
