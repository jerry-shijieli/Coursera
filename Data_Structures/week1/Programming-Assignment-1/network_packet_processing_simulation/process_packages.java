import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
    }

    public Response Process(Request request) {
        // write your code here
        // If buffer is empty
        if (this.finish_time_.isEmpty()){
            this.finish_time_.add(request.arrival_time+request.process_time);
            return new Response(false, request.arrival_time);
        }
        // when packet arrive but buffer is not empty
        // while (!this.finish_time_.isEmpty()){
        //     if (this.finish_time_.get(0) <= request.arrival_time)
        //         this.finish_time_.remove(0);
        //     else
        //         break;
        // }
        // if (!this.finish_time_.isEmpty())
        //     Collections.sort(this.finish_time_); //sort finish time
        int index = 0, count=0;
        while (index<this.finish_time_.size()){
            if (this.finish_time_.get(index++) <= request.arrival_time)
                 count = index;
        }

        if (!this.finish_time_.isEmpty()){
            ArrayList<Integer> to_process_list = new ArrayList<Integer>();
            for (int i=count; i<this.finish_time_.size(); i++)
                to_process_list.add(this.finish_time_.get(i));
            this.finish_time_ = to_process_list; // remove all processed
        }

        if (this.finish_time_.size() == this.size_) // buffer full, then drop
            return new Response(true, -1); 
        else if (this.finish_time_.isEmpty()){ // all previous packets are processed
            this.finish_time_.add(request.arrival_time+request.process_time);
            return new Response(false, request.arrival_time); 
        } else { // wait in buffer
            int start_time = this.finish_time_.get(this.finish_time_.size()-1);
            this.finish_time_.add(start_time+request.process_time);
            return new Response(false, start_time);
        }
        // return new Response(false, -1);
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
