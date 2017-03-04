import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {
    //write your code here
    int n=s.length(), m=t.length();
    int[][] dist = new int[n+1][m+1];
    // fill the edge elements
    for (int i=0; i<=n; i++){
      dist[i][0] = i;
    }
    for (int j=0; j<=m; j++){
      dist[0][j] = j;
    }
    for (int j=1; j<=m; j++){
      for (int i=1; i<=n; i++){
        int insertion = dist[i][j-1] + 1;
        int deletion = dist[i-1][j] + 1;
        int mismatch = dist[i-1][j-1] + 1;
        int match = dist[i-1][j-1];
        if (s.charAt(i-1)==t.charAt(j-1))
          dist[i][j] = Math.min(insertion, Math.min(deletion, match));
        else
          dist[i][j] = Math.min(insertion, Math.min(deletion, mismatch));
      }
    }
    return dist[n][m];
  }
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistance(s, t));
  }

}
