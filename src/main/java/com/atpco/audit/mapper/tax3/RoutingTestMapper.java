package com.atpco.audit.mapper.tax3;

import com.atpco.audit.model.RoutingTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ydc on 2019/8/14.
 */
public interface RoutingTestMapper {

    List<RoutingTest> query(@Param("taxAmount") int taxAmount, @Param("diffLow") String diffLow, @Param("diffUp") String diffUp);
}
