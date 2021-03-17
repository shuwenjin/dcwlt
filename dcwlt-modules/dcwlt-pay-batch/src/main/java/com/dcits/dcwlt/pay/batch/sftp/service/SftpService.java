package com.dcits.dcwlt.pay.batch.sftp.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;

public interface SftpService {

    /**
     *
     * 通过 sftp 下载文件
     *
     * @param targetPath 文件路径
     * @return
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     */
    File downloadFile(String targetPath) throws Exception;
}
