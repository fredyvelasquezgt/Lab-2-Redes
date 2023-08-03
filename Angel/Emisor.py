def viterbi_encoder(input_bits):
    g1 = [1, 1, 1]   # 1 + D + D^2
    g2 = [1, 0, 1]   # 1 + D^2

    # Utils
    encoded_bits = []
    state = [0, 0] 

    for bit in input_bits:
        output1 = state[0] ^ state[1] ^ bit
        output2 = state[0] ^ bit

        state = [bit] + state[:-1]

        encoded_bits.extend([output1, output2])

    return encoded_bits

input_bits = [1, 1, 0, 1, 0, 1] 
encoded_bits = viterbi_encoder(input_bits)
print("Trama codificada:", encoded_bits)
