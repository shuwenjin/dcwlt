package com.dcits.dcwlt.common.pay.sequence.mapper;

import com.dcits.dcwlt.common.pay.sequence.entity.SegmentAllocatorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 号段模式mapper
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月9日
 * @Version 1.0
 */
@Mapper
public interface SegAllocMapper {

    SegmentAllocatorEntity getLeafAlloc(@Param("tag") String tag);

    void updateMaxId(SegmentAllocatorEntity segAlloc);

    void initMaxId(SegmentAllocatorEntity segAlloc);

    List<String> getAllTags();

}
