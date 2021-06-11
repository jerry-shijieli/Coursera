import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
    if (n <= 1) return (long) n;

    long a = 0, b = 1, res = 0;
    for (int i = 2; i <= n; i++) {
      res = a + b;
      a = b;
      b = res;
    }

    return res;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(calc_fib(n));
  }
}
