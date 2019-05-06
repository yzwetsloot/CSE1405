package assignment1;

public class Tutorial {
    public static void main(String[] args) {
        // Showing binary representation of an integer value for debugging:
        long integerNotation = 6;
        String binaryNotation = Long.toBinaryString(integerNotation);
        System.out.println(integerNotation + " -> " + binaryNotation);

        // Creating integer from binary notation:
        long integer = Long.parseLong("110", 2);
        System.out.println(integer);


        // Creating 010110001 (bit sequences) from scratch
        long res = 0;
        res |= 1L << 7; // or res = res | (1L << 7)
        res |= 1L << 5;
        res |= 1L << 4;
        res |= 1L;
        System.out.println(Long.toBinaryString(res));


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
        long appended = original2 << 3 | appendee;
        System.out.println(Long.toBinaryString(original2) + " -> " + Long.toBinaryString(appended));


        // Changing the bit sequence 11(010)11 to 11(100)11
        long unchanged = Long.parseLong("1101011", 2);
        long newValue = Long.parseLong("100", 2);
        long mask = Long.parseLong("1100011", 2); // Create a mask that selects the parts that need to remain
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
