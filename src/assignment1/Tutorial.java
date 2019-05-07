package assignment1;

public class Tutorial {
    /*
    & bitwise AND
    | bitwise OR
    ^ bitwise XOR
    ~ bitwise complement
    << left shift || for all shifts holds that if you shift by more than 32 it will overflow
                     if it's not a 64-bit integer
    >> right shift
    >>> zero fill right shift
    */
    public static void main(String[] args) {
        long integerNotation = 6;
        String binaryNotation = Long.toBinaryString(integerNotation);
        System.out.println(integerNotation + " -> " + binaryNotation);

        long integer = Long.parseLong("110", 2);
        System.out.println(integer);

        long exampleInteger = 7;
        long exampleFromBinary = Long.parseLong("111", 2);
        if (exampleInteger == exampleFromBinary)
            System.out.println("111 -> 7");

        String a = "00111100"; // = 60
        String b = "00001101"; // = 13
        System.out.println("Binary string representation of 60: " + a);
        System.out.println("Binary string representation of 13: " + b);
        long resultXOR = 13 ^ 60;
        System.out.println("XOR operator applied to 60 and 13: " + resultXOR);
        long resultAND = 13 & 60;
        System.out.println("AND operator applied to 60 and 13: " + resultAND);
        long resultOR = 13 | 60;
        System.out.println("OR operator appleid to 60 and 13: " + resultOR);
        long resultNOT = ~60;
        System.out.println("NOT operator applied to 60: " + resultNOT);
        // Bitwise operators can be applied to integer types: long, int, short, char and byte
        // Examples of shift operators:
        long resultLeftShift = 60 << 5;
        System.out.println("Shift value left by 5 bits: " + Long.toBinaryString(resultLeftShift));
        System.out.println("Long value after shift left by 5: " + resultLeftShift);
        long resultRightShift = 60 >> 5;
        System.out.println("Shift value right by 5 bits: " + Long.toBinaryString(resultRightShift));
        System.out.println("Long value after shift right by 5: " + resultRightShift);

        // >>> zero fill right shift operator: pad shifted values with 0s
        long resultZeroFillRightShift = 60 >>> 3;
        System.out.println("Binary representation of below: " + Long.toBinaryString(resultZeroFillRightShift));
        // toBinaryString removes padding zeros
        System.out.println("Zero fill right shift by 6 on 60: " + resultZeroFillRightShift + "\n");

        long bitSequence = Long.parseLong("110", 2);
        System.out.println("Amount of 1s in 110: " + Long.bitCount(bitSequence));

        // Length of bitsequence measured from first 1
        long stringLengthExample = Long.parseLong("10110110", 2);
        System.out.println("Length of 10110110: " +
                ((long)Math.floor(Math.log(stringLengthExample)/Math.log(2)) + 1));

        // Creating 010110001 (bit sequences) from scratch
        long res = 0;
        res |= 1L << 7;
        res |= 1L << 5;
        res |= 1L << 4;
        res |= 1L;
        System.out.println("\n" + Long.toBinaryString(res));


        // Flipping a bit in 10(1)1 to become 10(0)1
        long unflipped = Long.parseLong("1011", 2);
        long flipped = unflipped ^ (1L << 1); // XOR original value with 10
        System.out.println(Long.toBinaryString(unflipped) + " -> " + Long.toBinaryString(flipped));


        // Prepending the bit sequence 1101110 with 101
        long original = Long.parseLong("1101110", 2);
        long prependee = Long.parseLong("101", 2);
        long prepended = original | (prependee << 7);
        System.out.println(Long.toBinaryString(original) + " -> " + Long.toBinaryString(prepended));


        // Appending the bit sequence 01110001 with 011
        long original2 = Long.parseLong("01110001", 2);
        long appendee = Long.parseLong("011", 2);
        long appended = original2 << 3 | appendee; // Simple OR combines 2 strings
        System.out.println(Long.toBinaryString(original2) + " -> " + Long.toBinaryString(appended));


        // Changing the bit sequence 11(010)11 to 11(100)11
        long unchanged = Long.parseLong("1101011", 2);
        long newValue = Long.parseLong("100", 2);
        long mask = Long.parseLong("1100011", 2);
        // Mask has 1s in the positions that have to be selected.
        long changed = unchanged & mask; // Clear part to be changed
        changed |= (newValue << 2); // Apply new value
        System.out.println(Long.toBinaryString(unchanged) + " -> " + Long.toBinaryString(changed));

        // Inserting a '1' in between bit sequence 110011010 to become 110(1)011010
        long uninserted = Long.parseLong("110011010", 2);
        // Masks can be created using the creating bit sequences from scratch method
        long mask1 = Long.parseLong("111000000", 2); // Mask to select first part
        long mask2 = Long.parseLong("000111111", 2); // Mask to select second part
        long tmp1 = ((mask1 & uninserted) << 1) | (mask2 & uninserted); // Create a gap
        long inserted = tmp1 | (1L << 6); // Add the value 1000000
        System.out.println(Long.toBinaryString(uninserted) + " -> " + Long.toBinaryString(inserted));
    }
}
