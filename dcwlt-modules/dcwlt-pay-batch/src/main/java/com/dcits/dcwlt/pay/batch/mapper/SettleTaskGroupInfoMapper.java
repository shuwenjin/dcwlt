package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 17:10
 */

@Mapper
public interface SettleTaskGroupInfoMapper {


    public SettleTaskGroupInfoDO queryTaskGroupInfoToExec(String taskGroupCode);
}
