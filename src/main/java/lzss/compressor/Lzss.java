package lzss.compressor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Lzss extends AbstractCompressor {

    private static final short RING_SIZE = 4096;
    private static final short RING_WRAP = RING_SIZE - 1;
    private static final int MAX_STORE_LENGTH = 18;
    private static final int THRESHOLD = 3;
    private static final short NOT_USED = RING_SIZE;

    private short matchPosition;
    private short matchLength;

    private byte[] ringBuffer = new byte[RING_SIZE + MAX_STORE_LENGTH - 1];
    private short[] dad = new short[RING_SIZE + 1];
    private short[] leftSon = new short[RING_SIZE + 1];
    private short[] rightSon = new short[RING_SIZE + 257];


    private ByteArrayOutputStream out;


    public Lzss(InputStream input) {
        super(input);
    }

    public ByteArrayOutputStream compress() throws
                                            IOException {
        out = new ByteArrayOutputStream(BUF_SIZE);

        short i; // an iterator
        short r; // node number in the binary tree
        short s; // position in the ring buffer
        short len; // length of initial string
        short lastMatchLength; // length of last match
        short codeBufPos; // position in the output buffer
        byte[] codeBuff = new byte[17]; // the output buffer
        byte mask; // bit mask for byte 0 of out input
        byte c; // character read from string

        initTree();
        codeBuff[0] = 0;
        codeBufPos = 1;
        mask = 1;

        s = 0;
        r = RING_SIZE - MAX_STORE_LENGTH;
        Arrays.fill(ringBuffer, 0, r, (byte) ' ');
        int readResult = input.read(ringBuffer, r, MAX_STORE_LENGTH);

        if (readResult <= 0) {
            return out;
        }

        len = (short) readResult;

        for (i = 1; i <= MAX_STORE_LENGTH; i++) {
            insertNode((short) (r - i));
        }

        insertNode(r);

        do {

            // matchLength may be spuriously long near the end of text.
            if (matchLength > len) {
                matchLength = len;
            }

            // Is it cheaper to store this as a single character? If so, make it
            // so.
            if (matchLength < THRESHOLD) {
                // Send one character. Remember that codeBuff[0] is the
                // set of flags for the next eight items.
                matchLength = 1;
                codeBuff[0] |= mask;
                codeBuff[codeBufPos++] = ringBuffer[r];
            } else {
                // Otherwise, we do indeed have a string that can be stored
                // compressed to save space.

                // The next 16 bits need to contain the position (12 bits)
                // and the length (4 bits).
                codeBuff[codeBufPos++] = (byte) matchPosition;
                codeBuff[codeBufPos++] = (byte) (((matchPosition >> 4) & 0xF0) | (matchLength - THRESHOLD));
            }

            // Shift the mask one bit to the left so that it will be ready
            // to store the new bit.
            mask <<= 1;

            // If the mask is now 0, then we know that we have a full set
            // of flags and items in the code buffer. These need to be
            // output.
            if (mask == 0) {
                // codeBuff is the buffer of characters to be output.
                // codeBufPos is the number of characters it contains.
                out.write(codeBuff, 0, codeBufPos);

                // Reset for next buffer...
                codeBuff[0] = 0;
                codeBufPos = 1;
                mask = 1;
            }

            lastMatchLength = matchLength;

            for (i = 0; i < lastMatchLength; i++) {

                readResult = input.read();
                if (readResult == -1) {
                    break;
                }
                c = (byte) readResult;

                deleteNode(s);
                ringBuffer[s] = c;

                if (s < MAX_STORE_LENGTH - 1) {
                    ringBuffer[s + RING_SIZE] = c;
                }

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);
                insertNode(r);
            }

            while (i++ < lastMatchLength) {
                deleteNode(s);

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                if (--len != 0) {
                    insertNode(r); /* buffer may not be empty. */
                }
            }

        } while (len > 0);

        if (codeBufPos > 1) {
            out.write(codeBuff, 0, codeBufPos);
        }

        return out;
    }

    public ByteArrayOutputStream uncompress() throws IOException {
        return uncompress(BUF_SIZE);
    }

    public ByteArrayOutputStream uncompress(int expectedSize) throws IOException {
        out = new ByteArrayOutputStream(expectedSize);

        byte[] c = new byte[MAX_STORE_LENGTH]; // an array of chars
        byte flags; // 8 bits of flags

        int r = RING_SIZE - MAX_STORE_LENGTH;
        Arrays.fill(ringBuffer, 0, r, (byte) ' ');

        flags = 0;
        int flagCount = 0; // which flag we're on

        while (true) {
            if (flagCount > 0) {
                flags = (byte) (flags >> 1);
                flagCount--;
            } else {
                int readResult = input.read();
                if (readResult == -1) {
                    break;
                }

                flags = (byte) (readResult & 0xFF);
                flagCount = 7;
            }

            if ((flags & 1) != 0) {
                if (input.read(c, 0, 1) != 1) {
                    break;
                }

                out.write(c[0]);

                ringBuffer[r] = c[0];
                r = (short) ((r + 1) & RING_WRAP);
            } else {
                if (input.read(c, 0, 2) != 2) {
                    break;
                }

                short pos = (short) ((c[0] & 0xFF) | ((c[1] & 0xF0) << 4));
                short len = (short) ((c[1] & 0x0F) + THRESHOLD);

                for (int k = 0; k < len; k++) {
                    c[k] = ringBuffer[(pos + k) & RING_WRAP];

                    ringBuffer[r] = c[k];
                    r = (r + 1) & RING_WRAP;
                }

                out.write(c, 0, len);
            }
        }
        return out;
    }

    private void initTree() {
        Arrays.fill(dad, 0, dad.length, NOT_USED);
        Arrays.fill(leftSon, 0, leftSon.length, NOT_USED);
        Arrays.fill(rightSon, 0, rightSon.length, NOT_USED);
    }

    private void insertNode(short pos) {
        assert pos >= 0 && pos < RING_SIZE;
        int cmp = 1;
        short key = pos;

        short p = (short) (RING_SIZE + 1 + (ringBuffer[key] & 0xFF));
        assert p > RING_SIZE;

        leftSon[pos] = NOT_USED;
        rightSon[pos] = NOT_USED;
        matchLength = 0;

        while (true) {
            if (cmp >= 0) {
                if (rightSon[p] != NOT_USED) {
                    p = rightSon[p];
                } else {
                    rightSon[p] = pos;
                    dad[pos] = p;
                    return;
                }
            } else {
                if (leftSon[p] != NOT_USED) {
                    p = leftSon[p];
                } else {
                    leftSon[p] = pos;
                    dad[pos] = p;
                    return;
                }
            }

            short i = 0;
            for (i = 1; i < MAX_STORE_LENGTH; i++) {
                cmp = (ringBuffer[key + i] & 0xFF) - (ringBuffer[p + i] & 0xFF);
                if (cmp != 0) {
                    break;
                }
            }

            if (i > matchLength) {
                matchPosition = p;
                matchLength = i;

                if (i >= MAX_STORE_LENGTH) {
                    break;
                }
            }
        }

        dad[pos] = dad[p];
        leftSon[pos] = leftSon[p];
        rightSon[pos] = rightSon[p];

        dad[leftSon[p]] = pos;
        dad[rightSon[p]] = pos;

        if (rightSon[dad[p]] == p) {
            rightSon[dad[p]] = pos;
        } else {
            leftSon[dad[p]] = pos;
        }

        dad[p] = NOT_USED;
    }

    private void deleteNode(short node) {
        assert node >= 0 && node < (RING_SIZE + 1);

        short q;

        if (dad[node] == NOT_USED) {
            return;
        }

        if (rightSon[node] == NOT_USED) {
            q = leftSon[node];
        } else if (leftSon[node] == NOT_USED) {
            q = rightSon[node];
        } else {
            q = leftSon[node];
            if (rightSon[q] != NOT_USED) {
                do {
                    q = rightSon[q];
                } while (rightSon[q] != NOT_USED);

                rightSon[dad[q]] = leftSon[q];
                dad[leftSon[q]] = dad[q];
                leftSon[q] = leftSon[node];
                dad[leftSon[node]] = q;
            }

            rightSon[q] = rightSon[node];
            dad[rightSon[node]] = q;
        }

        dad[q] = dad[node];

        if (rightSon[dad[node]] == node) {
            rightSon[dad[node]] = q;
        } else {
            leftSon[dad[node]] = q;
        }

        dad[node] = NOT_USED;
    }
}