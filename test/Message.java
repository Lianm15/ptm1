package test;

import java.util.Date;

public class Message {

    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String asText) {
        this.data = asText.getBytes();
        this.asText = asText;
        this.asDouble = tryParseDouble(asText);
        this.date = new Date();

    }

    public Message(byte[] data)
    {
        this(new String(data));
    }

    public Message(double asDouble)
    {
        this(Double.toString(asDouble));
    }

    private double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return Double.NaN;
        }
    }



}
