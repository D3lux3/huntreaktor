import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {
        try {
            Scanner fileReader = new Scanner(new File("data.txt"));
            while (fileReader.hasNext()) {
                List<String> bytes = new ArrayList<>();
                String channel = fileReader.nextLine();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < channel.length(); i++) {
                    sb.append(channel.charAt(i));
                    if (sb.length() == 8) {
                        bytes.add(sb.toString());
                        sb = new StringBuilder();
                    }
                }
                boolean valid = false;
                char passwordChar = '*';
                for (int i = 1; i < bytes.size() + 1; i++) {
                    int value = Integer.valueOf(bytes.get(i - 1), 2);
                    if (value + 1 > bytes.size() && valid) {
                        passwordChar = (char) (value);
                        break;
                    } else if (value >= bytes.size()) {
                        continue;
                    } else {
                        i = value;
                        valid = true;
                        continue;
                    }
                }
                System.out.print(passwordChar);
            }
        } catch (Error | FileNotFoundException e) {
            System.out.println(e);
        }

    }

}
