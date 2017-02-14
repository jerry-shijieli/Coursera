import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
    if (n <= 1)
      return n;

    long f0 = 0, f1=1, f2=0;
    for (int i=2; i<=n; i++){
        f2 = f1 + f0;
        f0 = f1;
        f1 = f2;
    }
    return f2;
    //return calc_fib(n - 1) + calc_fib(n - 2);
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(calc_fib(n));
  }
}
