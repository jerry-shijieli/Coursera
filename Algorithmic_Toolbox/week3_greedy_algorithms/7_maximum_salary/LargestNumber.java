import java.util.*;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here
        StringBuilder result = new StringBuilder();
        Arrays.sort(a, (c1, c2) -> isGreaterOrEqual(c2, c1));
        for (int i = 0; i < a.length; i++) {
            result.append(a[i]);
        }
        return result.toString();
    }

    private static int isGreaterOrEqual(String a, String b){
        if (a.length()==0 && b.length()==0) return 0;
        if (a.length()==0) return -1;
        if (b.length()==0) return 1;
        int result = 0;
        if (a.charAt(0) > b.charAt(0))
            result = 1;
        else if (a.charAt(0) < b.charAt(0))
            result = -1;
        else {
            int x = Integer.parseInt(a+b), y = Integer.parseInt(b+a);
            result = x - y;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

