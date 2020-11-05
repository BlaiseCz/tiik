package lzss.compressor;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A packer provides the ability to compress and uncompress block text.
 * Implementing classes are expected to provide a way to supply the input.
 *
 * @see gnu.lgpl.License for license details.<br>
 *      The copyright to this program is held by it's authors.
 * @author DM Smith [dmsmith555 at yahoo dot com]
 */
public interface Compressor {
    /**
     * The size to read/write when unzipping a compressed byte array of unknown
     * size.
     */
    int BUF_SIZE = 2048;

    /**
     * Compresses the input and provides the result.
     *
     * @return the compressed result
     */
    ByteArrayOutputStream compress() throws IOException;

    /**
     * Uncompresses the input and provides the result.
     *
     * @return the uncompressed result
     */
    ByteArrayOutputStream uncompress() throws IOException;

    /**
     * Uncompresses the input and provides the result.
     *
     * @param expectedLength
     *            the size of the result buffer
     * @return the uncompressed result
     */
    ByteArrayOutputStream uncompress(int expectedLength) throws IOException;
}