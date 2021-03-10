package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/9 17:00
 */

@Mapper
public interface SettleTaskInfoMapper {

    public List<SettleTaskInfoDO> queryTaskInfoListToExec(String taskGroupCode);
}
