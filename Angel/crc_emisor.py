def crc_calculation(input_bits, crc_polynomial):
    # Crear una copia de los bits de entrada
    remaining_bits = input_bits.copy()

    # Inicializar la lista de cálculo
    calculation_result = []

    # Calcular los primeros bits utilizando el polinomio CRC
    for _ in range(len(crc_polynomial)):
        calculation_result.append(remaining_bits.pop(0))

    # Procesar los bits restantes de la trama
    while len(remaining_bits) > 0:
        # Aplicar XOR entre los bits de cálculo y el polinomio CRC
        for i in range(len(crc_polynomial)):
            calculation_result[i] ^= crc_polynomial[i]

        # Eliminar ceros al inicio de cálculo
        while calculation_result[0] == 0 and len(calculation_result) > 0:
            calculation_result.pop(0)

        # Agregar bits restantes de entrada a cálculo
        while len(calculation_result) < len(crc_polynomial) and len(remaining_bits) > 0:
            calculation_result.append(remaining_bits.pop(0))

    # Realizar XOR final con el polinomio CRC si las longitudes coinciden
    if len(crc_polynomial) == len(calculation_result):
        for i in range(len(crc_polynomial)):
            calculation_result[i] ^= crc_polynomial[i]

    # Asegurarse de que cálculo tenga la longitud adecuada
    calculation_result = calculation_result[-(len(crc_polynomial) - 1):]

    return calculation_result

def add_crc_to_bits(input_bits, crc_result):
    return input_bits[:-(len(crc_result))] + crc_result

def generate_crc_code(polynomial):
    return [int(bit) for bit in polynomial]

def input_text_with_crc(data, crc_polynomial):
    # Convertir la cadena de entrada a una lista de bits
    input_bits = [int(bit) for bit in data]

    # Generar el código CRC
    crc_code = generate_crc_code(crc_polynomial)

    # Agregar ceros al final de los bits de entrada para el cálculo CRC
    input_bits += [0] * (len(crc_code) - 1)

    # Realizar el cálculo CRC
    crc_result = crc_calculation(input_bits, crc_code)

    # Agregar el resultado CRC a los bits de entrada y obtener la trama completa
    complete_frame = add_crc_to_bits(input_bits, crc_result)

    # Convertir la trama con CRC a una cadena de bits
    result = ''.join(str(bit) for bit in complete_frame)
    return result

# Definir polinomio CRC
crc_32_polynomial = [1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1]

# Ejemplo de uso
input_data = "110101"
output = input_text_with_crc(input_data, crc_32_polynomial)
print("Resultado:", output)
