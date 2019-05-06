package assignment1;

public class Tutorial {
    public static void main(String[] args) {
        long integerNotation = 6;
        String binaryNotation = Long.toBinaryString(integerNotation);
        System.out.println(integerNotation + " -> " + binaryNotation);

        long integer = Long.parseLong("110", 2);
        System.out.println(integer);

        long res = 0;
        res |= 1L << 7;
        res |= 1L << 5;
        res |= 1L << 4;
        res |= 1L;
        System.out.println(Long.toBinaryString(res));

        long unflipped = Long.parseLong("1011", 2);
        long flipped = unflipped ^ (1L << 1);
        System.out.println(Long.toBinaryString(unflipped) + " -> " + Long.toBinaryString(flipped));

        long original = Long.parseLong("1101110", 2);
        long prependee = Long.parseLong("101", 2);
        long prepended = original | (prependee << 7);
        System.out.println(Long.toBinaryString(original) + " -> " + Long.toBinaryString(prepended));

        long original2 = Long.parseLong("01110001", 2);
        long appendee = Long.parseLong("011", 2);
        long appended = original2 << 3 | appendee;
        System.out.println(Long.toBinaryString(original2) + " -> " + Long.toBinaryString(appended));

        long unchanged = Long.parseLong("1101011", 2);
        long newValue = Long.parseLong("100", 2);
        long mask = Long.parseLong("1100011", 2);
        long changed = unchanged & mask;
        changed |= (newValue << 2);
        System.out.println(Long.toBinaryString(unchanged) + " -> " + Long.toBinaryString(changed));

        long uninserted = Long.parseLong("110011010", 2);
        long mask1 = Long.parseLong("111000000", 2);
        long mask2 = Long.parseLong("000111111", 2);
        long tmp1 = ((mask1 & uninserted) << 1) | (mask2 & uninserted);
        long inserted = tmp1 | (1L << 6);
        System.out.println(Long.toBinaryString(uninserted) + " -> " + Long.toBinaryString(inserted));
    }
}
