import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
    class Row implements Comparable<Row>{
        String data;

        Row(String text) {
            this.data = new String(text);
        }

        @Override
        public int compareTo(Row r2){
            return this.data.compareTo(r2.data);
        }
    }

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    String BWT(String text) {
        StringBuilder result = new StringBuilder();

        // write your code here
        Row[] matrix = new Row[text.length()];
        matrix[0] = new Row(text);
        for (int i=1; i<text.length(); i++){
            String rotation = text.substring(i) + text.substring(0,i);
            matrix[i] = new Row(rotation);
        }
        Arrays.sort(matrix);
        for (int i=0; i<text.length(); i++){
            result.append(matrix[i].data.charAt(text.length()-1));
        }

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
