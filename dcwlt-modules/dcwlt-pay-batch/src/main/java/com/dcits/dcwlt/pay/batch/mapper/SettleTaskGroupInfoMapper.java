package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskGroupInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp Ashin
 * @date: 2021/3/9 17:10
 */

@Mapper
public interface SettleTaskGroupInfoMapper {

    SettleTaskGroupInfoDO queryTaskGroupInfoToExec(String taskGroupCode);

    List<SettleTaskGroupInfoDO> select(SettleTaskGroupInfoDO settleTaskGroupInfoDO);

    int deleteByPrimaryKey(String[] taskgroupcode);

    int insert(SettleTaskGroupInfoDO record);

    int updateByPrimaryKeySelective(SettleTaskGroupInfoDO record);

}
