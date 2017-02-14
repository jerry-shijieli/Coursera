import java.util.*;

public class LCM {
  private static long GCD(int a, int b){
    if (b == 0)
      return (long) a;

    int c = a % b;
    return GCD(b, c);
  }

  private static long lcm_naive(int a, int b) {
    // for (long l = 1; l <= (long) a * b; ++l)
    //   if (l % a == 0 && l % b == 0)
    //     return l;
    long gcd = GCD(a, b);

    return (long) a / gcd * b;
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcm_naive(a, b));
  }
}
