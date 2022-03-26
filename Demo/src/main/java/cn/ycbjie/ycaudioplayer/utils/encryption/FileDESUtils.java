package cn.ycbjie.ycaudioplayer.utils.encryption;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yc on 2018/2/7.
 * 将音视频文件中的每个字节都经过加密算法进行了加密，显示安全性比较高，耗时也长。
 */

public class FileDESUtils {


    /**
     * 这个方法只是解释使用方法，并无意义
     */
    private void utils(){
        FileDESUtils fileDES = null;
        try {
            fileDES = new FileDESUtils("spring.sky");
            //加密调用这个方法
            fileDES.doEncryptFile("d:/a.mp4", "d:/b");
            //解密调用这个方法
            //fileDES.doDecryptFile("d:/b");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密解密的key
     */
    private Key mKey;
    /**
     * 解密的密码
     */
    private Cipher mDecryptCipher;
    /**
     * 加密的密码
     */
    private Cipher mEncryptCipher;

    public FileDESUtils(String key) throws Exception {
        initKey(key);
        initCipher();
    }

    /**
     * 创建一个加密解密的key
     *
     * @param keyRule
     */
    public void initKey(String keyRule) {
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        mKey = new SecretKeySpec(byteTemp, "DES");
    }

    /***
     * 初始化加载密码
     *
     * @throws Exception
     */
    private void initCipher() throws Exception {
        mEncryptCipher = Cipher.getInstance("DES");
        mEncryptCipher.init(Cipher.ENCRYPT_MODE, mKey);
        mDecryptCipher = Cipher.getInstance("DES");
        mDecryptCipher.init(Cipher.DECRYPT_MODE, mKey);

    }

    /**
     * 加密文件
     *
     * @param in
     * @param savePath 加密后保存的位置
     */
    public void doEncryptFile(InputStream in, String savePath) {
        if (in == null) {
            System.out.println("inputstream is null");
            return;
        }
        try {
            CipherInputStream cin = new CipherInputStream(in, mEncryptCipher);
            OutputStream os = new FileOutputStream(savePath);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = cin.read(bytes)) > 0) {
                os.write(bytes, 0, len);
                os.flush();
            }
            os.close();
            cin.close();
            in.close();
            System.out.println("加密成功");
        } catch (Exception e) {
            System.out.println("加密失败");
            e.printStackTrace();
        }
    }

    /**
     * 加密文件
     *
     * @param filePath 需要加密的文件路径
     * @param savePath 加密后保存的位置
     * @throws FileNotFoundException
     */
    public void doEncryptFile(String filePath, String savePath) throws FileNotFoundException {
        doEncryptFile(new FileInputStream(filePath), savePath);
    }


    /**
     * 解密文件
     *
     * @param in
     */
    public void doDecryptFile(InputStream in, String path) {
        if (in == null) {
            System.out.println("inputstream is null");
            return;
        }
        try {
            CipherInputStream cin = new CipherInputStream(in, mDecryptCipher);
            OutputStream outputStream = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = cin.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
            cin.close();
            in.close();
            System.out.println("解密成功");
        } catch (Exception e) {
            System.out.println("解密失败");
            e.printStackTrace();
        }
    }

    /**
     * 解密文件
     *
     * @param filePath 文件路径
     * @throws Exception
     */
    public void doDecryptFile(String filePath, String outPath) throws Exception {
        doDecryptFile(new FileInputStream(filePath), outPath);
    }

}
