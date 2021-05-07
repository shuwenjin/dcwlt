package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.SettleTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: some desc
 * @author: zhangp Ashin
 * @date: 2021/3/9 17:00
 */

@Mapper
public interface SettleTaskInfoMapper {

    List<SettleTaskInfoDO> queryTaskInfoListToExec(String taskGroupCode);

    int deleteByPrimaryKey(String[] taskcodes);

    int insertSelective(SettleTaskInfoDO record);

    SettleTaskInfoDO selectByPrimaryKey(String taskcode);

    List<SettleTaskInfoDO> select(SettleTaskInfoDO settleTaskInfoDO);

    int updateByPrimaryKeySelective(SettleTaskInfoDO record);
}
