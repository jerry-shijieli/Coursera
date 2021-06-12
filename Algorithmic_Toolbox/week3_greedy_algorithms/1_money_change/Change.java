import java.util.Scanner;

public class Change {
    private enum Coin {
        DIME (10),
        NICKEL (5),
        CENT (1);

        private final int value;

        Coin(final int value) {
            this.value = value;
        }

        private int getValue() {
            return value;
        }
    }

    private static int getChange(int m) {
        //write your code here
        int count = 0;
        int rem = m;

        while (rem > 0) {
            if (rem < Coin.DIME.getValue()) {
                break;
            }
            rem -= Coin.DIME.getValue();
            count++;
        }
        while (rem > 0) {
            if (rem < Coin.NICKEL.getValue()) {
                break;
            }
            rem -= Coin.NICKEL.getValue();
            count++;
        }
        while (rem > 0) {
            if (rem < Coin.CENT.getValue()) {
                break;
            }
            rem -= Coin.CENT.getValue();
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}

