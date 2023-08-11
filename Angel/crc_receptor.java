package crc;

import java.util.*;

public class CRCReceptor {

    public static void main(String[] args) {
        String clientInput = "110101001011001"; // Ejemplo de entrada del cliente
        List<Integer> crcPolynomial = Arrays.asList(1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);

        String result = verifyAndExtractData(clientInput, crcPolynomial);
        System.out.println("Resultado de la trama: " + result);
    }

    // Convierte una cadena binaria en una lista de bits
    public static List<Integer> convertToBitList(String data) {
        List<Integer> bitList = new ArrayList<>();
        for (char bit : data.toCharArray()) {
            bitList.add(Character.getNumericValue(bit));
        }
        return bitList;
    }

    // Realiza el cálculo CRC para verificar la integridad de la trama
    public static boolean performCrcCheck(List<Integer> dataBits, List<Integer> crcPolynomial) {
        List<Integer> crcBits = new ArrayList<>(dataBits.subList(0, crcPolynomial.size()));

        for (int i = crcPolynomial.size(); i < dataBits.size(); i++) {
            if (crcBits.get(0) == 1) {
                for (int j = 0; j < crcPolynomial.size(); j++) {
                    crcBits.set(j, crcBits.get(j) ^ crcPolynomial.get(j));
                }
            }
            crcBits.remove(0);
            crcBits.add(dataBits.get(i));
        }

        if (crcBits.get(0) == 1) {
            for (int j = 0; j < crcPolynomial.size(); j++) {
                crcBits.set(j, crcBits.get(j) ^ crcPolynomial.get(j));
            }
        }

        return !crcBits.contains(1);
    }

    // Verifica la integridad de la trama y extrae los datos si es válida
    public static String verifyAndExtractData(String clientInput, List<Integer> crcPolynomial) {
        List<Integer> dataBits = convertToBitList(clientInput);

        boolean crcResult = performCrcCheck(dataBits, crcPolynomial);

        if (crcResult) {
            List<Integer> dataWithoutCrc = dataBits.subList(0, dataBits.size() - crcPolynomial.size() + 1);
            StringBuilder dataWithoutCrcString = new StringBuilder();
            for (int bit : dataWithoutCrc) {
                dataWithoutCrcString.append(bit);
            }
            return dataWithoutCrcString.toString();
        } else {
            return "Error";
        }
    }
}
