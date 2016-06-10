import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {
    //write your code here
    int m = s.length(), n = t.length();
    char[] schar = s.toCharArray(), tchar = t.toCharArray();
    int[][] dist = new int[m+1][n+1];
    for (int i=0; i<=m; i++)
    	dist[i][0] = i;
    for (int j=0; j<=n; j++)
    	dist[0][j] = j;
    for (int i=1; i<=m; i++){
    	for (int j=1; j<=n; j++){
    		int insertion = dist[i-1][j] + 1;
    		int deletion = dist[i][j-1] + 1;
    		int match = dist[i-1][j-1];
    		int mismatch = dist[i-1][j-1] + 1;
    		if (schar[i-1] == tchar[j-1]){
    			dist[i][j] = Math.min(match, Math.min(insertion, deletion));
    		} else {
    			dist[i][j] = Math.min(mismatch, Math.min(insertion, deletion));
    		}
    	}
    }
    return dist[m][n];
  }
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistance(s, t));
  }

}
