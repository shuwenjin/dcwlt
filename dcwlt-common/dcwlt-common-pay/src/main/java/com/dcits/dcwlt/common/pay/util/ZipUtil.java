package com.dcits.dcwlt.common.pay.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 18:44
 */
public class ZipUtil {

    public static final int BSIZE = 8*1024;

    public static void unZip(String filePath, String fileParent) {
        try(InputStream in=new FileInputStream(new File(filePath))) {
            xxunzip(in, fileParent);
            //return true;
        } catch (IOException e) {
            //return false;
            e.printStackTrace();
        }
    }

    private static void xxunzip(InputStream is, String outdir) throws IOException {
        try(ZipInputStream zis = new ZipInputStream (new BufferedInputStream(is))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(outdir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    xxunzipcpio(zis, file);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static void xxunzipcpio(ZipInputStream zis, File file) {
        try(BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(file),BSIZE)) {
            cpio(new BufferedInputStream(zis), bos, "unzip:" + file.getName());
            bos.flush();
        } catch(IOException e) {
          e.printStackTrace();
        }
    }

    public static long cpio (InputStream in, OutputStream out, String wt) throws IOException {
        byte[] buffer = new byte[BSIZE];
        int c;
        long tot = 0;
        while ((c = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, c);
            tot += c;
        }
        return tot;
    }

}
