function viterbiDecoder(receivedBits) {
    const g1 = [1, 1, 1]; // 1 + D + D^2
    const g2 = [1, 0, 1]; // 1 + D^2

    // Utils
    const decodedBits = [];
    let state = [0, 0]; 

    for (let i = 0; i < receivedBits.length; i += 2) {
        const input1 = receivedBits[i];
        const input2 = receivedBits[i + 1];

        const dist1 = (state[0] ^ input1) + (state[1] ^ input2);
        const dist2 = (state[0] ^ input2) + (state[1] ^ input1);

        state = [input2, state[0]];

        const decodedBit1 = (dist1 <= dist2) ? 0 : 1;
        const decodedBit2 = (dist1 <= dist2) ? 1 : 0;

        decodedBits.push(decodedBit1);
        decodedBits.push(decodedBit2);
    }

    return decodedBits;
}

const receivedBits = [1, 1, 0, 1, 0, 0, 0];

const decodedBits = viterbiDecoder(receivedBits);

console.log("Trama decodificada:", decodedBits.join(" "));
