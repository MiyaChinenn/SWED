public class UlamFunctionTest {
    public static void main(String[] args) {
        final int LIMIT = 1_000_000;

        for (int i = 1; i < LIMIT; i++) {
            if (!collatzTerminatesAtOne(i)) {
                System.out.println("Collatz sequence failed for n = " + i);
                return;
            }
        }

        System.out.println("Success: All numbers from 1 to 999,999 terminate at 1.");
    }

    public static boolean collatzTerminatesAtOne(int n) {
        long current = n;

        while (current != 1) {
            if (current % 2 == 0) {
                current /= 2;
            } else {
                current = 3 * current + 1;
            }
        }

        return true;
    }
}
