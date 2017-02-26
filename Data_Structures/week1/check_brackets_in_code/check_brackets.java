import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
                opening_brackets_stack.push(new Bracket(next, position+1));
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here
                if (opening_brackets_stack.isEmpty()){
                    opening_brackets_stack.push(new Bracket(next, position+1));
                    break;
                }
                Bracket tmp = opening_brackets_stack.peek();
                if(tmp.Match(next)){
                    opening_brackets_stack.pop();
                } else {
                    opening_brackets_stack.push(new Bracket(next, position+1));
                    break;
                }
            }
        }

        // Printing answer, write your code here
        if (opening_brackets_stack.isEmpty()){
            System.out.println("Success");
        } else {
            Bracket tmp = opening_brackets_stack.peek();
            char next = tmp.type;
            if (next == ')' || next == ']' || next == '}')
                System.out.println(tmp.position);
            else {
                while (!opening_brackets_stack.isEmpty()){
                    tmp = opening_brackets_stack.pop();
                }
                System.out.println(tmp.position);
            }
        }
    }
}
