package com.dcits.dcwlt.common.pay.util;


import com.dcits.dcwlt.common.pay.constant.FileConst;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.common.pay.exception.SettleTaskException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUtil {

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }


    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取虚构目标File对象
     *
     * @param file     File对象
     * @param origPath 原目录（提供目录保留目录往下的文件夹结构，null值不保留结构直接移动）
     * @param newPath  新目录
     * @return 目标路径虚构File
     */
    public static File getTargeFile(File file, String origPath, String newPath) {
        try {
            logger.info("origPath:" + origPath);
            logger.info("newPath:" + newPath);
            logger.info("file:" + file.getCanonicalPath());
            if (StringUtils.isBlank(origPath)) {
                origPath = file.getCanonicalPath().substring(0, file.getCanonicalPath().length());
            }
            return new File(file.getCanonicalPath().replaceAll(StringEscapeUtils.escapeJava(origPath), StringEscapeUtils.escapeJava((newPath))));
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 移动文件到垃圾场
     *
     * @param origFile
     * @return
     */
    public static File copyFileToLocalPath(File origFile) {
        //根据传入文件名
        String saveBakPath = FileConst.CONFIG_PROPERTY_SAVEBAKPATH;
        String localPath = FileConst.CONFIG_PROPERTY_APPDATASAVEPATH;
        File targetFile = FileUtil.getTargeFile(origFile, saveBakPath, localPath);
        if (targetFile == null) {
            logger.info("文件移动至本地文件夹失败 Mothod:backupFileToFormatFail，文件名：%s", origFile.getName());
            return null;
        }
        if (copyFile(origFile, targetFile, true)) {
            return targetFile;
        } else {
            return null;
        }
    }

    /**
     * 移动文件 返回true和false 后期使用异常捕获
     *
     * @param origFile
     * @param targetFile
     * @return
     */
    @SuppressWarnings("finally")
    public static Boolean copyFile(File origFile, File targetFile, Boolean reWrite) {
        logger.info("origFile:" + origFile.getPath());
        logger.info("targetFile:" + targetFile.getPath());
        File targetDir = new File(targetFile.getParent());
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        if (targetFile.exists() && !reWrite) {
            logger.info(String.format("新文件已存在:%s", targetFile.getPath()));
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        } else {
            int byteread = 0;
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = new FileInputStream(origFile);
                fos = new FileOutputStream(targetFile, false);
                byte[] buffer = new byte[1444];
                while ((byteread = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }
            } catch (Exception e) {
                logger.error("Stream close IOException:"
                        + e.getMessage());
                logger.error(String.format("复制文件失败:%s -> %s", origFile.getPath(), targetFile.getPath()));
                throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
            } finally {
                ResUtil.closeResource(fos, is);
            }
        }
        return true;
    }


    public static String swapSenderIdRecverId(String fileName) {
        logger.info("转换前:%s", fileName);
        String[] items = fileName.split("\\.", -1);
        if (items.length != 5) {
            logger.error("无法识别的文件名：%s", fileName);
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(items[1]);
        sb.append(".");
        sb.append(items[0]);
        sb.append(".");
        sb.append(items[2]);
        sb.append(".");
        sb.append(items[3]);
        sb.append(".");
        sb.append(items[4]);
        logger.info("转换后:%s", sb.toString());
        return sb.toString();
    }

    /**
     * 组合文件路径及文件名识别路径是否 / 结尾 文件名是否 / 开头 改为有且仅有一个的合并全名返回
     *
     * @param path     路径
     * @param fileName 文件名
     * @return
     */
    public static String mergeFilePathName(String path, String fileName) {
        String fileFullName = null;
        if (fileName.startsWith(File.separator)) {
            fileName = fileName.substring(1);
        }
        if (!path.endsWith(File.separator)) {
            fileFullName = path + File.separator + fileName;
        } else {
            fileFullName = path + fileName;
        }
        return fileFullName;
    }


    public static <T> T readFile(String path, String encoding, String type) {
        StringBuilder lastStr = new StringBuilder();

        FileInputStream fileInputStream = null;
        InputStreamReader inputStream = null;
        BufferedReader bufferReader = null;
        try {
            fileInputStream = new FileInputStream(path);
            inputStream = new InputStreamReader(fileInputStream, encoding);
            bufferReader = new BufferedReader(inputStream);
            String tempString = null;
            while ((tempString = bufferReader.readLine()) != null) {
                lastStr.append(tempString);
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
            throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
            throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
        } finally {
            ResUtil.closeResource(bufferReader, inputStream, fileInputStream);
        }
        if ("byte[]".equals(type)) {
            byte[] b = lastStr.toString().getBytes();
            return (T) b;
        } else {
            return (T) lastStr.toString();
        }
    }

    public static void writeFile(String path, String context, String encoding, boolean isReWrite, boolean isAppend) {

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                logger.error("目录创建失败:" + file.getParentFile());
                throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
            }
        }
        if (file.exists()) {
            if (!isReWrite) {
                logger.error(String.format("文件已存在且不允许重写文件:%s", path));
                throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
            } else {
                logger.error(String.format("文件已存在重写文件:%s", path));
            }
        } else {
            try {
                if (!file.createNewFile()) {
                    logger.error(String.format("文件创建失败:%s", path));
                    throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
                }
            } catch (IOException e) {
                logger.error(String.format("文件创建失败:%s", path));
                throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
            }
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file, isAppend);
            osw = new OutputStreamWriter(fos, encoding);
            bw = new BufferedWriter(osw);
            bw.write(context);
            bw.flush();
        } catch (FileNotFoundException e) {
            logger.error("文件不存在");
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        } catch (UnsupportedEncodingException e) {
            throw new SettleTaskException(SettleTaskErrorEnum.PARAMETER_ERROR);
        } catch (IOException e) {
            throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
        } catch (Exception e) {
            logger.error("其他异常");
            throw new SettleTaskException(SettleTaskErrorEnum.DSPT_SYS_ERROR);
        } finally {
            ResUtil.closeResource(bw, osw, fos);
        }
    }


    /**
     * 判断指定的文件是否存在。
     * @param fileName 要判断的文件的文件名
     * @return 存在时返回true，否则返回false。
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }


    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [path]
     * @Return: boolean
     * @Author: zhangp
     * @Date: 2021/3/11 18:27
     */
    public static boolean delete( String path ) {
        if(path == null || path.trim().length() == 0){
            return false;
        }
        return delete(new File(path));
    }
    public static boolean delete( File fileOrDirectory ) {
        if (fileOrDirectory == null) {return false;}
        if (!fileOrDirectory.exists()){return false;}
        if (fileOrDirectory.isDirectory()) {
            File[] files = fileOrDirectory.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    delete(childFile);
                }
            }
        }
        return fileOrDirectory.delete();
    }
}
