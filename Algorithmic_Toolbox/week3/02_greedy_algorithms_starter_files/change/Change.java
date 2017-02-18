import java.util.Scanner;

public class Change {

    private static int getChange(int m) {
        //write your code here
        int result = 0;
        int[] changes = {10, 5, 1};
        for (int i=0; i<changes.length; i++){
            int tmp = m, count=0;
            while (tmp >= changes[i]){
                tmp -= changes[i];
                count++;
            }
            result += count;
            m %= changes[i];
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

