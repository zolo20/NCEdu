package ru.ars.ncedu.task4_5.algorithm.decode.string;

public class InapplicableFormatException extends RuntimeException {
    /**
     * Invalid line format for string.
     */
    public InapplicableFormatException() {
        super();
    }

    /**
     * Constructs an <code>IInapplicableFormatException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public InapplicableFormatException(String s) {
        super(s);
    }
}
