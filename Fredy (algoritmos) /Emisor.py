def calc_redundant_bits(message_length):
    r = 0
    while 2**r < message_length + r + 1:
        r += 1
    return r

def pos_redundant_bits(data, redundant_bits):
    m = len(data)
    res = ''
    j = 0
    k = 1
    
    for i in range(1, m + redundant_bits + 1):
        if i == 2 ** j:
            res += '0'
            j += 1
        else:
            res += data[-k]
            k += 1
            
    return res[::-1]

def calc_parity_bits(arr, redundant_bits):
    n = len(arr)
    
    for i in range(redundant_bits):
        val = 0
        for j in range(1, n + 1):
            if j & (2 ** i) == (2 ** i):
                val ^= int(arr[-j])
                
        arr = arr[:n - (2 ** i)] + str(val) + arr[n - (2 ** i) + 1:]
    
    return arr

def calculate_data(data):
    print("Data antes de Hamming:", data)
    message_length = len(data)
    redundant_bits = calc_redundant_bits(message_length)
    data_with_redundancy = pos_redundant_bits(data, redundant_bits)
    data_with_parity = calc_parity_bits(data_with_redundancy, redundant_bits)
    return data_with_parity

if __name__ == "__main__":
    data = input("Ingresa los datos: ")
    calculated_data = calculate_data(data)
    print("Datos transferidos:", calculated_data)

