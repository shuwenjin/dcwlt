package com.dcits.dcwlt.pay.api.domain.dcep.gwf008;


import com.dcits.dcwlt.common.pay.constant.ApiConstant;
import com.dcits.dcwlt.common.pay.constant.BusiConst;

/**
 * 文件下载请求报文工厂
 *
 * @author
 * @date 2021/1/6
 */
public final class Gwf008ReqFactory {
    private Gwf008ReqFactory() {}

    //private static RtpUtil rtpUtil = RtpUtil.getInstance();

    /**
     *
     * @param fileName  文件名
     * @param filePath  文件路径
     * @param exChangeKey
     * @param transDate 业务日期 保证业务日期唯一
     * @param overWrite 文件覆盖标识 用于文件有修改但是文件名重复时不可下载场景，添加此标识可重新下载文件   1：覆盖
     * @return
     */
    public static Gwf008ReqDTO getGwf008ReqDTO(String fileName,
                                               String filePath, String exChangeKey,
                                               String transDate, String overWrite){

        //获取配置信息
        //目标系统商户号 该字段枚举值在联调阶段请找开放平台开发确认后分配
//        String merId = rtpUtil.getProperty(BusiConst.FILE_SERVICE_MERID);
//        //目标系统APPID 目标系统在开放平台的系统标识
//        String appId = rtpUtil.getProperty(BusiConst.FILE_SERVICE_APPID);
//        //源系统商户号
//        String srcMerId = rtpUtil.getProperty(BusiConst.FILE_SERVICE_SRCMERID, ApiConstant.DEFAULT_SER_MERID);
//        //源系统标识 源系统在开放平台的系统标识
//        String channel = rtpUtil.getProperty(BusiConst.FILE_SERVICE_CHANNEL);
//        String fileType = rtpUtil.getProperty(BusiConst.FILE_SERVICE_FILETYPE);
//
        //初始化请求报文实体
        Gwf008ReqDTO gwf008ReqDTO = new Gwf008ReqDTO();
//        gwf008ReqDTO.setMerId(merId);
//        gwf008ReqDTO.setAppId(appId);
//        gwf008ReqDTO.setSrcMerId(srcMerId);
//        gwf008ReqDTO.setChannel(channel);
//        gwf008ReqDTO.setFileType(fileType);
        gwf008ReqDTO.setFileName(fileName);
        gwf008ReqDTO.setFilePath(filePath);
        gwf008ReqDTO.setTransDate(transDate);
        gwf008ReqDTO.setOverWrite(overWrite);
        gwf008ReqDTO.setExChangeKey(exChangeKey);

        return gwf008ReqDTO;
    }
}
