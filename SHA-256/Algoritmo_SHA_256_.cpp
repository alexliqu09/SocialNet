#include <iomanip>
#include <iostream>
#include <sstream>
#include <vector>

typedef unsigned char BYTE;
typedef unsigned int WORD;
typedef unsigned long long ll;

// Constants used in hash algorithm
const WORD K[] = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                  0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                  0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                  0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                  0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                  0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                  0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                  0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

std::vector<std::vector<WORD>> M; // Mensaje a ser encriptado
std::vector<std::vector<WORD>> H; // Mensaje encriptado (Hash)
std::vector<BYTE> bytes;          
WORD W[64];                       
ll l = 0;                         // Longitud del mensaje en binario
int N;                            // Numero de bloques de 512 bits

//Constantes auxiliares
WORD a, b, c, d, e, f, g, h;

//Constantes temporales
WORD T1, T2;


const void store_message_bytes(const std::string &line)
{
    int str_length = line.length();
    for (int i = 0; i < str_length; ++i)
    {
        bytes.push_back(line[i]);
        l += 8;
    }
}

//Cantidad de ceros que falta rellenar hasta 448 bits.
const int calc_padding()
{
    int k = 0;
    while ((l + 1 + k) % 512 != 448) ++k;
    return k;
}

//Agregamos los valores correspondientes.
const void pad_message()
{
    int k = calc_padding();
    //Agregamos un 1 al vector.
    bytes.push_back(0x80);
    k = k - 7;

    //Agregamos los ceros faltantes.
    for (int i = 0; i < (k / 8); ++i)
    {
        bytes.push_back(0);
    }

    //Finalmente agregamos la longitud del mensaje en binario.
    for (int i = 1; i < 9; ++i)
    {
        bytes.push_back(l >> (64 - i * 8));
    }
}


const void parse_message()
{
    WORD n = 0;
    for (int i = 0; n < bytes.size() / 64; ++n)
    {
        std::vector<WORD> block(16);
        for (int j = 0; j < 16; ++j)
        {
            WORD word = 0;
            for (int k = 0; k < 4; ++k, ++i)
            {
                word <<= 8;
                word |= bytes[i];
            }
            block[j] = word;
        }
        M.push_back(block);
    }
    N = n;
}

//Inicializamos los hashes h0-7.
const void init_hash()
{
    std::vector<WORD> h0 = {0x6a09e667,
                            0xbb67ae85,
                            0x3c6ef372,
                            0xa54ff53a,
                            0x510e527f,
                            0x9b05688c,
                            0x1f83d9ab,
                            0x5be0cd19};
    H.push_back(h0);
}

//FUNCIONES
//Rotate Right
const WORD ROTR(const WORD &n, const WORD &x)
{
    return (x >> n) | (x << (32 - n));
}

//Right shift
const WORD SHR(const WORD &n, const WORD &x)
{
    return x >> n;
}


const WORD Ch(const WORD &x, const WORD &y, const WORD &z)
{
    return (x & y) ^ (~x & z);
}


const WORD Maj(const WORD &x, const WORD &y, const WORD &z)
{
    return (x & y) ^ (x & z) ^ (y & z);
}


const WORD lsigma0(const WORD &x)
{
    return ROTR(2, x) ^ ROTR(13, x) ^ ROTR(22, x);
}


const WORD lsigma1(const WORD &x)
{
    return ROTR(6, x) ^ ROTR(11, x) ^ ROTR(25, x);
}


const WORD ssigma0(const WORD &x)
{
    return ROTR(7, x) ^ ROTR(18, x) ^ SHR(3, x);
}


const WORD ssigma1(const WORD &x)
{
    return ROTR(17, x) ^ ROTR(19, x) ^ SHR(10, x);
}

//Hallamos el valor hash.
const void compute_hash()
{
    std::vector<WORD> hash_block(8);
    for (int i = 1; i <= N; ++i)
    {
       
        for (int t = 0; t <= 15; ++t)
            W[t] = M[i - 1][t]; 
        for (int t = 16; t <= 63; ++t)
            W[t] = ssigma1(W[t - 2]) + W[t - 7] + ssigma0(W[t - 15]) + W[t - 16];

        // Inicializamos las variables auxiliares
        a = H[i - 1][0];
        b = H[i - 1][1];
        c = H[i - 1][2];
        d = H[i - 1][3];
        e = H[i - 1][4];
        f = H[i - 1][5];
        g = H[i - 1][6];
        h = H[i - 1][7];

        
        for (int t = 0; t <= 63; ++t)
        {
            T1 = h + lsigma1(e) + Ch(e, f, g) + K[t] + W[t];
            T2 = lsigma0(a) + Maj(a, b, c);
            h = g;
            g = f;
            f = e;
            e = d + T1;
            d = c;
            c = b;
            b = a;
            a = T1 + T2;
        }

        //Calculamos los valores temporales de h0-7
        hash_block[0] = a + H[i - 1][0];
        hash_block[1] = b + H[i - 1][1];
        hash_block[2] = c + H[i - 1][2];
        hash_block[3] = d + H[i - 1][3];
        hash_block[4] = e + H[i - 1][4];
        hash_block[5] = f + H[i - 1][5];
        hash_block[6] = g + H[i - 1][6];
        hash_block[7] = h + H[i - 1][7];
        H.push_back(hash_block);
    }
}

const void output_hash()
{
    //Concatenamos los hashes ara formar el hash final.
    for (int i = 0; i < 8; ++i)
        std::cout << std::hex << std::setw(8) << std::setfill('0') << H[N][i];
    std::cout << std::endl;
}


const void clear()
{
    M.clear();
    H.clear();
    bytes.clear();
    l = 0;
}

int main()
{

    for (std::string line; std::getline(std::cin, line);) {

        store_message_bytes(line);

        pad_message();

        parse_message();

        init_hash();

        compute_hash();

        output_hash();

        clear();
    }
}