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
        long generatorLength = length(generatorSequence);
        if((inputLength > generatorLength) && (generatorLength > 1)) {
            long mask = 0;
            mask |= 1L << generatorLength - 1;
            mask |= 1L;
            boolean validGeneratorSequence = (Long.bitCount(generatorSequence & mask) == 2);
            if(!validGeneratorSequence) {
                return -1;
            }
            long message = bitSequence << generatorLength - 1;
            message = division(inputLength, generatorSequence, message);
            return message;
        }
        else {
            return -1;
        }
    }

    private static long length(long bitSequence) {
        return (long) (Math.floor(Math.log(bitSequence) / Math.log(2)) + 1);
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

    private static long division(int inputLength, long generatorSequence, long message) {
        System.out.println("Message: " + Long.toBinaryString(message));

        long temp = generatorSequence << (inputLength - 1);
        System.out.println("Generator sequence divisor: " + Long.toBinaryString(temp));
        for(int i = 0; i < inputLength; i++) {
            if(length(message) == length(temp)) {
                message = message ^ temp;
                System.out.println(Long.toBinaryString(message));
                temp = temp >> 1L;
            }
            else {
                temp = temp >> 1L;
                continue;
            }
        }
        return message;
    }

    public static void main(String[] args) {
        long bitSequence = Long.parseLong("1101011111", 2);
        long generatorSequence = Long.parseLong("10011", 2);

        System.out.println(calculateCRC(bitSequence, 14, generatorSequence));
        System.out.println(checkCRC(bitSequence, 14, generatorSequence, 2));
    }
}
