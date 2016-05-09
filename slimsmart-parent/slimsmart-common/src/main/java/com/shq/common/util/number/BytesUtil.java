package com.shq.common.util.number;

/**
 *
 */
public final class BytesUtil
{
    /**
     *
     */
    private BytesUtil()
    {
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static int toInt(byte[] bytes)
    {
        int result = 0;
        for (int i = 0; i < 4; i++)
        {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static short toShort(byte[] bytes)
    {
        return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8) - (short) Byte.MIN_VALUE + (short) bytes[1]);
    }

    /**
     *
     * @param value
     * @return
     */
    public static byte[] toBytes(int value)
    {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--)
        {
            result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
            value >>>= 8;
        }
        return result;
    }

    /**
     *
     * @param value
     * @return
     */
    public static byte[] toBytes(short value)
    {
        byte[] result = new byte[2];
        for (int i = 1; i >= 0; i--)
        {
            result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
            value >>>= 8;
        }
        return result;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println(0 + "==" + BytesUtil.toInt(BytesUtil.toBytes(0)));
        System.out.println(1 + "==" + BytesUtil.toInt(BytesUtil.toBytes(1)));
        System.out.println(-1 + "==" + BytesUtil.toInt(BytesUtil.toBytes(-1)));
        System.out.println(Integer.MIN_VALUE + "==" + BytesUtil.toInt(BytesUtil.toBytes(Integer.MIN_VALUE)));
        System.out.println(Integer.MAX_VALUE + "==" + BytesUtil.toInt(BytesUtil.toBytes(Integer.MAX_VALUE)));
        System.out.println(Integer.MIN_VALUE / 2 + "==" + BytesUtil.toInt(BytesUtil.toBytes(Integer.MIN_VALUE / 2)));
        System.out.println(Integer.MAX_VALUE / 2 + "==" + BytesUtil.toInt(BytesUtil.toBytes(Integer.MAX_VALUE / 2)));
    }

}






