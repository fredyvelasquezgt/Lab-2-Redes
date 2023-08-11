public class Receptor {

    public static int calculateRedundantBits(int messageLength) {
        // Calculate the number of required redundant bits
        for (int i = 0; i < messageLength; i++) {
            if (Math.pow(2, i) >= messageLength + i + 1) {
                return i;
            }
        }
        return -1; // Adjust return value as needed for your logic
    }

    public static String placeRedundantBits(String data, int redundantBits) {
        // Place redundant bits at positions corresponding to powers of 2
        int j = 0;
        int k = 1;
        int m = data.length();
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= m + redundantBits; i++) {
            if (i == Math.pow(2, j)) {
                result.append('0');
                j++;
            } else {
                result.append(data.charAt(data.length() - k));
                k++;
            }
        }

        return result.reverse().toString();
    }

    public static String calculateParityBits(String arr, int redundantBits) {
        int n = arr.length();

        for (int i = 0; i < redundantBits; i++) {
            int val = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & (1 << i)) == (1 << i)) {
                    val = val ^ Integer.parseInt(String.valueOf(arr.charAt(arr.length() - j)));
                }
            }
            arr = arr.substring(0, n - (1 << i)) + val + arr.substring(n - (1 << i) + 1);
        }

        return arr;
    }

    public static int detectError(String arr, int redundantBits) {
        int n = arr.length();
        int res = 0;

        for (int i = 0; i < redundantBits; i++) {
            int val = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & (1 << i)) == (1 << i)) {
                    val = val ^ Integer.parseInt(String.valueOf(arr.charAt(arr.length() - j)));
                }
            }
            res = res + val * (int) Math.pow(10, i);
        }

        // Convert binary to decimal
        return Integer.parseInt(String.valueOf(res), 2);
    }

    public static String errorCorrector(String receivedData) {
        int mReceived = receivedData.length();
        int rReceived = calculateRedundantBits(mReceived);
    
        String arrReceived = placeRedundantBits(receivedData, rReceived);
        arrReceived = calculateParityBits(arrReceived, rReceived);
        int correction = detectError(receivedData, rReceived);
    
        if (correction == 0) {
            StringBuilder originalData = new StringBuilder();
            int j = 0;
            for (int i = 1; i <= receivedData.length(); i++) {
                if (i != Math.pow(2, j)) {
                    originalData.append(receivedData.charAt(receivedData.length() - i));
                } else {
                    j++;
                }
            }
            System.out.println("Decoded Original Message: " + originalData.reverse());
            return originalData.toString(); 
        } else {
            int errorIndex = arrReceived.length() - correction + 1;
            if (errorIndex <= 0) {
                System.out.println("Error detected in received frame at position: Out of bounds");
                return "Error";
            } else {
                System.out.println("Error detected in received frame at position: " + errorIndex + " from the left.");
                String correctedData = arrReceived.substring(0, arrReceived.length() - correction) +
                        (1 - Integer.parseInt(String.valueOf(arrReceived.charAt(arrReceived.length() - correction)))) +
                        arrReceived.substring(arrReceived.length() - correction + 1);
                return correctedData;
            }
        }
    }

    public static void main(String[] args) {
        String receivedData = "01101010001101111011011100011010001111";
        String correctedData = errorCorrector(receivedData);
        System.out.println("Corrected Data: " + correctedData);
    }
}

