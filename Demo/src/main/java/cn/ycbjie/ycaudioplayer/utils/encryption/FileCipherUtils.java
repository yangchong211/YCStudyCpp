package cn.ycbjie.ycaudioplayer.utils.encryption;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;

/**
 * Created by yc on 2018/2/7.
 */

public class FileCipherUtils {

    /**
     * 加密后的文件的后缀
     */
    private static final String CIPHER_TEXT_SUFFIX = ".yc";
    /**
     * 加解密时以32K个字节为单位进行加解密计算
     */
    private static final int CIPHER_BUFFER_LENGTH = 32 * 1024;

    /**
     * 加密，这里主要是演示加密的原理，没有用什么实际的加密算法
     * @param filePath  明文文件绝对路径
     * @return          是否加密成功
     */
    public static boolean encrypt(String filePath, CipherProgressListener listener) {
        if(filePath == null){
            return false;
        }
        FileChannel channel = null;
        RandomAccessFile raf = null;
        try {
            long startTime = System.currentTimeMillis();
            File f = new File(filePath);

            raf = new RandomAccessFile(f, "rw");
            long totalLength = raf.length();
            channel = raf.getChannel();
            long multiples = totalLength / CIPHER_BUFFER_LENGTH;
            long remainder = totalLength % CIPHER_BUFFER_LENGTH;

            MappedByteBuffer buffer ;
            byte tmp;
            byte rawByte;
            //先对整除部分加密
            for(int i = 0; i < multiples; i++){
                buffer = channel.map(FileChannel.MapMode.READ_WRITE,
                        i * CIPHER_BUFFER_LENGTH, (i + 1) * CIPHER_BUFFER_LENGTH);
                //此处的加密方法很简单，只是简单的异或计算
                for (int j = 0; j < CIPHER_BUFFER_LENGTH; ++j) {
                    rawByte = buffer.get(j);
                    tmp = (byte) (rawByte ^ j);
                    buffer.put(j, tmp);
                    if(null != listener){
                        listener.onProgress(i * CIPHER_BUFFER_LENGTH + j, totalLength);
                    }
                }
                buffer.force();
                buffer.clear();
            }

            //对余数部分加密
            buffer = channel.map(FileChannel.MapMode.READ_WRITE,
                    multiples * CIPHER_BUFFER_LENGTH, multiples * CIPHER_BUFFER_LENGTH + remainder);
            for (int j = 0; j < remainder; ++j) {
                rawByte = buffer.get(j);
                tmp = (byte) (rawByte ^ j);
                buffer.put(j, tmp);
                if(null != listener){
                    listener.onProgress(multiples * CIPHER_BUFFER_LENGTH + j, totalLength);
                }
            }
            buffer.force();
            buffer.clear();
            //对加密后的文件重命名，增加.cipher后缀
            //noinspection ResultOfMethodCallIgnored
            f.renameTo(new File(f.getPath() + CIPHER_TEXT_SUFFIX));
            AppLogUtils.e("加密用时："+(System.currentTimeMillis() - startTime) /1000 + "s");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(channel!=null){
                    channel.close();
                }
                if(raf!=null){
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 解密，这里主要是演示加密的原理，没有用什么实际的加密算法
     * @param filePath  密文文件绝对路径，文件需要以.cipher结尾才会认为其实可解密密文
     * @return          是否解密成功
     */
    public static boolean decrypt(String filePath, CipherProgressListener listener) {
        if(filePath == null){
            return false;
        }
        FileChannel channel = null;
        RandomAccessFile raf = null;
        try {
            long startTime = System.currentTimeMillis();
            File f = new File(filePath);
            if(!f.getPath().toLowerCase().endsWith(CIPHER_TEXT_SUFFIX)){
                //后缀不同，认为是不可解密的密文
                return false;
            }
            raf = new RandomAccessFile(f, "rw");
            long totalLength = raf.length();
            channel = raf.getChannel();

            long multiples = totalLength / CIPHER_BUFFER_LENGTH;
            long remainder = totalLength % CIPHER_BUFFER_LENGTH;

            MappedByteBuffer buffer ;
            byte tmp;
            byte rawByte;

            //先对整除部分解密
            for(int i = 0; i < multiples; i++){
                buffer = channel.map(FileChannel.MapMode.READ_WRITE,
                        i * CIPHER_BUFFER_LENGTH, (i + 1) * CIPHER_BUFFER_LENGTH);
                //此处的解密方法很简单，只是简单的异或计算
                for (int j = 0; j < CIPHER_BUFFER_LENGTH; ++j) {
                    rawByte = buffer.get(j);
                    tmp = (byte) (rawByte ^ j);
                    buffer.put(j, tmp);
                    if(null != listener){
                        listener.onProgress(i * CIPHER_BUFFER_LENGTH + j, totalLength);
                    }
                }
                buffer.force();
                buffer.clear();
            }

            //对余数部分解密
            buffer = channel.map(FileChannel.MapMode.READ_WRITE,
                    multiples * CIPHER_BUFFER_LENGTH, multiples * CIPHER_BUFFER_LENGTH + remainder);
            for (int j = 0; j < remainder; ++j) {
                rawByte = buffer.get(j);
                tmp = (byte) (rawByte ^ j);
                buffer.put(j, tmp);
                if(null != listener){
                    listener.onProgress(multiples * CIPHER_BUFFER_LENGTH + j, totalLength);
                }
            }
            buffer.force();
            buffer.clear();

            //对加密后的文件重命名，增加.cipher后缀
            //noinspection ResultOfMethodCallIgnored
            f.renameTo(new File(f.getPath().substring(f.getPath().toLowerCase().indexOf(CIPHER_TEXT_SUFFIX))));
            AppLogUtils.e("解密用时："+(System.currentTimeMillis() - startTime) / 1000 + "s");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(channel!=null){
                    channel.close();
                }
                if(raf!=null){
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public interface CipherProgressListener{
        /**
         * 用于加解密进度的监听器
         * @param current           当前进度值
         * @param total             总的
         */
        void onProgress(long current, long total);
    }


}
