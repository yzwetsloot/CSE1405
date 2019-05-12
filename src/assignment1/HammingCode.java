package assignment1;

public class HammingCode {

    /**
     * Calculates the hamming code of the given bit sequence.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The Hamming code sequence
     */
    public static long calcHamming(long bitSequence, int inputLength, boolean isEvenParity) {
        if (inputLength != 0) {
            long parityLength = 0;
            while (!((inputLength + parityLength + 1) <= Math.pow(2, parityLength))) {
                parityLength++;
            }
            long mask = 1L << inputLength - 1;
            long codeWord = 0;
            for (int i = 1; i <= (inputLength + parityLength); i++) {
                if (powerOfTwo(i)) {
                    codeWord |= 1L << inputLength + parityLength - i;
                }
            }
            long value;
            for (int i = 1; i <= (inputLength + parityLength); i++) {
                if (!powerOfTwo(i)) {
                    value = mask & bitSequence;
                    value = value << (inputLength + parityLength - i) - length(mask) + 1;
                    codeWord = codeWord | value;
                    mask = mask >> 1;
                }
            }
            long countParity = 1;
            for (int i = 1; i <= (inputLength + parityLength); i++) {
                if (powerOfTwo(i)) {
                    long countOne = 0;
                    long selectDataBit = 1L << countParity - 1;
                    for (long j = 1; j <= (inputLength + parityLength); j++) {
                        if ((selectDataBit & j) != 0) {
                            long checkOne = 1L << (inputLength + parityLength - j);
                            if ((checkOne & codeWord) != 0) {
                                countOne++;
                            }
                        }
                    }
                    long parityPosition;
                    if (isEvenParity) {
                        if (countOne % 2 == 1) {
                            parityPosition = 1L << (inputLength + parityLength - i);
                            codeWord ^= parityPosition;
                        }
                    } else {
                        if (countOne % 2 == 0) {
                            parityPosition = 1L << (inputLength + parityLength - i);
                            codeWord ^= parityPosition;
                        }
                    }
                    countParity++;
                }
            }
            return codeWord;
        }
        return 0;
    }

    /**
     * Returns the corrected (if needed) hamming code of the given bit sequence.
     *
     * @param bitSequence The Hamming code bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The correct Hamming code sequence
     */
    public static long checkHamming(long bitSequence, int inputLength, boolean isEvenParity) {
        if (inputLength > 2) {
            long errorSyndrome = 0;
            long countParity = 1;
            for (int i = 1; i <= inputLength; i++) {
                if (powerOfTwo(i)) {
                    long countOne = 0;
                    long selectDataBit = 1L << countParity - 1;
                    for (long j = 1; j <= inputLength; j++) {
                        if ((selectDataBit & j) != 0) {
                            long checkOne = 1L << (inputLength - j);
                            if ((checkOne & bitSequence) != 0) {
                                countOne++;
                            }
                        }
                    }
                    if (isEvenParity && !(countOne % 2 == 0)) {
                        errorSyndrome |= selectDataBit;
                    }
                    if (!isEvenParity && (countOne % 2 == 0)) {
                        errorSyndrome |= selectDataBit;
                    }
                    countParity++;
                }
            }
            long errorPosition = 0;
            if (errorSyndrome != 0) {
                errorPosition = 1L << (inputLength - errorSyndrome);
            }
            return errorPosition ^ bitSequence;
        }
        return 0;
    }

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
     * Checks whether input is a power of two or not.
     *
     * @param number Input to be checked whether power of 2 or not.
     * @return is power of two
     */
    private static boolean powerOfTwo(long number) {
        return (Math.log(number) / Math.log(2)) == Math.round(Math.log(number) / Math.log(2));
    }
}
