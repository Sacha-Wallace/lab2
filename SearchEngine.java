import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }
    
        int port = Integer.parseInt(args[0]);
    
        Server.start(port, new URLSplicer());
    }
}

class URLSplicer implements URLHandler{
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return strings.toString();
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                ArrayList<String> output = new ArrayList<String>();
                for (String string : strings) {
                    if(string.contains(parameters[1])){
                        output.add(string);
                    }
                }
                return output.toString();
            }
            return String.format("N/A");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("Strings increased by %s! It's now " + strings, parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}


