package com.auto.modal.io;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


/*
 * @Description: 文件io操作
 * @Param:
 * @Author: zl
 * @Date: 2019-03-17 19:06
 */
public class FileIo {
    private Runtime r = Runtime.getRuntime(); // 性能消耗内存
    private Long cost;

    // file
    public static boolean createFile(String filePath) {
        File f = new File(filePath);
        String dir = f.getPath().replace(f.getName(), "");
        File d = new File(dir);
        if (!d.isDirectory())
            DiskIo.createDir(dir);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }

    public static boolean deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        return true;
    }

    public static boolean removeFile(String oFilePath, String nFilePath) {
        File f = new File(oFilePath);
        String dir = nFilePath.replace(f.getName(), "");
        DiskIo.createDir(dir);
        File f1 = new File(nFilePath);
        if (!f.exists()) {
            return false;
        } else {
            f.renameTo(f1);
        }
        return true;
    }

    public static boolean copyFile(String oFilePath, String nFilePath) {
        //java7
        File f = new File(oFilePath);
        String dir = nFilePath.replace(f.getName(), "");
        DiskIo.createDir(dir);
        File f1 = new File(nFilePath);
        if (!f.exists()) {
            return false;
        } else {

            try {
                Files.copy(f.toPath(), f1.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean renameFile(String oleFileName, String newFileName) {
        File f = new File(oleFileName);
        File f1 = new File(newFileName);
        return f.renameTo(f1);

    }

    public static boolean compressFile(String filePath, String compassType) {
        return true;
    }

    public static boolean deCompressFile(String filePath, String compassType) {
        return true;
    }

    //fileInfo
    public static String getTxtFileEncoded(String filePath) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(filePath));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    public static long getFileSize(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return f.length();
        } else
            return 0;
    }

    public static String getCharset(String filePath) {
        return "";
    }

    public static boolean fileExists(String filePath) {
        File f = new File(filePath);
        if (f.exists())
            return true;
        else
            return false;
    }

    public static String getFileMd5(String filePath) {
        String md5 = "";
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }

    //file convert
    public static boolean byteArrToFile(byte[] byArr, String filePath, String charsetName) {
        //编码式转换
        String rs = null;
        try {
            rs = new String(byArr, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        if (createFile(filePath)) {
            writeTxtFile(filePath, rs, charsetName);

            return true;
        } else
            return false;
        /*
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream(byArr.length);
            try (FileOutputStream outF = new FileOutputStream(filePath)) {
                outF.write(byArr);
                outF.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
         */

    }

    public static byte[] fileToByte(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        } else {
            int fileSize = new Long(f.length()).intValue();
            byte[] byArr = new byte[fileSize];
            ByteArrayOutputStream outBuffer = new ByteArrayOutputStream(fileSize);
            try (FileInputStream fileInputStream = new FileInputStream(f)) {
                fileInputStream.read(byArr);
                return byArr;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

    }

    public static String bytesToString(byte[] byArr, String charsetName) {
        //编码式转换
        String rs = null;

        try {
            rs = new String(byArr, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        return rs;
    }

    public static String latin1ToUtf8(String s) {
        if (s != null) {
            try {
                int length = s.length();
                byte[] buffer = new byte[length];
                // 0x81 to Unicode 0x0081, 0x8d to 0x008d, 0x8f to 0x008f, 0x90
                // to 0x0090, and 0x9d to 0x009d.
                for (int i = 0; i < length; ++i) {
                    char c = s.charAt(i);
                    if (c == 0x0081) {
                        buffer[i] = (byte) 0x81;
                    } else if (c == 0x008d) {
                        buffer[i] = (byte) 0x8d;
                    } else if (c == 0x008f) {
                        buffer[i] = (byte) 0x8f;
                    } else if (c == 0x0090) {
                        buffer[i] = (byte) 0x90;
                    } else if (c == 0x009d) {
                        buffer[i] = (byte) 0x9d;
                    } else {
                        buffer[i] = Character.toString(c).getBytes("CP1252")[0];
                    }
                }
                String result = new String(buffer, "UTF-8");
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // file operate
    public static String readTxtFile(String filePath, String fileCoding) {
        /*
         * @Description: 获取文件所有内容并返回固定编码格式的字符串。
         * @Param: [filePath, fileCoding]
         * @return: java.lang.String
         * @Author: zl
         * @Date: 2019/3/28
         */
        File f = new File(filePath);
        Long fl = f.length();
        byte[] fileContent = new byte[fl.intValue()];
        try {
            FileInputStream fin = new FileInputStream(f);
            fin.read(fileContent);
            fin.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //编码式转换
        String rs = null;
        try {
            rs = new String(fileContent, fileCoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return rs;
    }

    public static List<String> readTxtFileLine(String filePath, String fileCoding) {
        List<String> fileLineList = new ArrayList<>();

        try {
            File file = new File(filePath);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), fileCoding);
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                fileLineList.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLineList;
    }

    public static String readBigFile(String filePath, String charsetName, Integer readFileSize) {
        //filesize<2g;readFileSize>0x300000
        byte[] fileContent = new byte[readFileSize];
        File f = new File(filePath);
        Long fl = f.length();
        final int BUFFER_SIZE = 0x300000;
        long start = System.currentTimeMillis();
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fl);
            byte[] dst = new byte[BUFFER_SIZE]; //每次读出3m
            for (int offset = 0; offset < fl; offset = offset + BUFFER_SIZE) {
                if (fl - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++) {
                        dst[i] = inputBuffer.get(offset + i);
                    }
                } else {
                    for (int i = 0; i < fl - offset; i++) {
                        dst[i] = inputBuffer.get(offset + i);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        long cost = System.currentTimeMillis() - start;
        //System.out.println("NIO 内存映射读大文件，总共耗时："+(cost)+"ms");
        //编码式转换
        String rs = null;
        try {
            rs = new String(fileContent, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    public static byte[] readOffBigFile(String filePath, long offPos, Integer readFilesize) {
        byte[] dst = new byte[readFilesize]; //每次读出字节数
        File f = new File(filePath);
        Long fl = f.length();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            FileChannel fileChannel = fis.getChannel();
            MappedByteBuffer inputBuffer = null;
            for (long offset = offPos; offset < fl; offset = offset + readFilesize) {
                if (fl - offset >= readFilesize) {
                    inputBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, offPos, readFilesize);
                    inputBuffer.get(dst);
                } else {
                    int size = (int) (fl - offPos);
                    dst = new byte[size];
                    inputBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, offPos, size);
                    inputBuffer.get(dst);
                }
                return dst;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dst;
    }


    public static boolean writeTxtFile(String filePath, String contents, String charsetName) {
        //
        OutputStreamWriter wR = null;
        try {
            wR = new OutputStreamWriter(new FileOutputStream(filePath, true), charsetName);
            wR.write(contents);
            wR.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wR != null) {
                    wR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean writeBigFile(String filePath, byte[] bytes) {
        FileOutputStream wR = null;
        try {
            wR = new FileOutputStream(filePath, true);
            wR.write(bytes);
            wR.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wR != null) {
                    wR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

    }

    public static boolean saveFile(String filePath, String contents, String fileCoding) {
        File f = new File(filePath);
        if (f.exists())
            deleteFile(filePath);
        createFile(filePath);
        writeTxtFile(filePath, contents, fileCoding);
        return true;
    }

    public static void main(String[] args) {

    }
}
