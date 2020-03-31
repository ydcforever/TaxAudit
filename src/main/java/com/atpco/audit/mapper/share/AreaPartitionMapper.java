package com.atpco.audit.mapper.share;

import com.atpco.audit.model.AreaPartition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by T440 on 2019/2/8.
 */
@Component
public interface AreaPartitionMapper {

    public AreaPartition queryAreaPartition(String airportCode);

    public List<AreaPartition> queryAreaPartitions(@Param("list") List<String> list);
}
