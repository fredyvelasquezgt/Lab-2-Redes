import java.io.*;
import java.net.*;
import crc.crc_receptor;
import Rodrigo.Receptor;

public class SocketServer {
    public static void main(String[] args) {
        int port = 12345;
        int successes = 0;
        int failures = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String clientInput;
                    
                    while ((clientInput = in.readLine()) != null) {
                        String[] data = clientInput.split(",");
                        int scanner = Integer.parseInt(data[2]);
                        
                        if (scanner == 1) {
                            String inputData = data[0];
                            int deci = Integer.parseInt(data[1]);
                            
                            if (deci == 1) {
                                clientInput = crc.crc_receptor.inputReciever(inputData);
                            } else if (deci == 2) {
                                System.out.println("Data received by Hamming: " + inputData); 
                                clientInput = Rodrigo.Receptor.ErrorCorrector(inputData);
                            } else {
                                System.out.println("ERROR, invalid option");
                            }

                            if (!clientInput.equals("Error")) {
                                System.out.println("Received modified: " + clientInput);
                                System.out.println(convertBinaryToText(clientInput));
                            } else {
                                System.out.println("Error: Unable to process due to incorrect message");
                            }
                        } else {
                            String inputData = data[0];
                            int deci = Integer.parseInt(data[1]);
                            
                            if (deci == 1) {
                                clientInput = crc.crc_receptor.inputReciever(inputData);
                            } else if (deci == 2 ){
                                clientInput = Rodrigo.Receptor.ErrorCorrector(inputData);
                            }

                            if (!clientInput.equals("Error")) {
                                successes++;
                            } else {
                                failures++;
                            }    
                        }
                    }
                    
                    System.out.println("Total successes: " + successes);
                    System.out.println("Total failures: " + failures);
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                } finally {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    public static String convertBinaryToText(String binaryString) {
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 8) {
            String section = binaryString.substring(i, Math.min(i + 8, binaryString.length()));
            int decimalValue = Integer.parseInt(section, 2);
            textBuilder.append((char) decimalValue);
        }
        return textBuilder.toString();
    }
}

