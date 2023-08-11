import socket
import string
import random
import matplotlib.pyplot as plt
import numpy as np
from crc.crc_emisor import *
from Rodrigo.Emisor import *

def apply_noise(data, error_probability):
    noisy_data = ''
    for bit in data:
        if random.random() < error_probability:
            noisy_bit = '1' if bit == '0' else '0'  # Flip the bit
            noisy_data += noisy_bit
        else:
            noisy_data += bit
    return noisy_data

def generate_example_text():
    lengths = np.random.randint(1, 21, size=1000)
    example_texts = [''.join(random.choice(string.ascii_lowercase) for _ in range(length)) for length in lengths]
    return example_texts

def graph_results(successes, failures):
    labels = ['Successes', 'Failures']
    values = [successes, failures]
    plt.bar(labels, values, color=['green', 'red'])
    plt.xlabel('Results')
    plt.ylabel('Count')
    plt.title('Successes vs Failures')
    plt.show()

def text_to_binary(text):
    return ''.join(format(ord(c), '08b') for c in text)

def send_data(data, port, deci, scanner):
    server_socket = ('localhost', port)
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect(server_socket)
        s.sendall(f"{data},{deci},{scanner}".encode())

if __name__ == "__main__":
    port = 12345
    seguir = True
    
    while seguir:
        print("=======================")
        print("1. Enviar mensaje")
        print("2. Realizar simulacion")
        print("3. Mostrar resultados de la simulación")
        print("4. Salir")
        
        scanner = int(input("Ingrese una opción: "))
        
        if scanner == 1:
            data = input("Ingresa el mensaje a enviar: ")
            data = text_to_binary(data)
            print("Data convertida a binario:", data)
            
            escogido = True
            while escogido:
                print("=======================")
                print("Escoja el modelo que desea utilizar")
                print("1. CRC-32")
                print("2. Hamming")
                deci = int(input("Ingrese una opción: "))
                
                if deci == 1:
                    data = inputText(data)
                    escogido = False
                elif deci == 2:
                    data = calculate_data(data)
                    print("Nueva data pasada por Hamming:", data)
                    escogido = False
                else:
                    print("Error, escoja una opción válida")
            
            error_probability = 0.01
            data = apply_noise(data, error_probability)
            send_data(data, port, deci, scanner)
            
        elif scanner == 2:
            example_texts = generate_example_text()
            escogido = True
            while escogido:
                print("=======================")
                print("Escoja el modelo que desea utilizar")
                print("1. CRC-32")
                print("2. Hamming")
                deci = int(input("Ingrese una opción: "))
                
                if deci == 1 or deci == 2:
                    escogido = False
                else:
                    print("Error, escoja una opción válida")
            
            for text in example_texts:
                print("Texto:", text)
                data = text_to_binary(text)
                
                if deci == 1:
                    data = inputText(data)
                elif deci == 2:
                    data = calculate_data(data)

                error_probability = 0.01
                data = apply_noise(data, error_probability)
                send_data(data, port, deci, scanner)
                
        elif scanner == 3:
            successes = int(input("Ingrese el dato 'successes' para la gráfica: "))
            failures = int(input("Ingrese el dato 'failures' para la gráfica: "))
            graph_results(successes, failures)
            
        elif scanner == 4:
            print("Saliendo")
            seguir = False
            
        else:
            print("Error, ingrese una opción válida")

