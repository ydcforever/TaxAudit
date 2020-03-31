import com.atpco.audit.mapper.share.AreaPartitionMapper;
import com.atpco.audit.model.AreaPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydc on 2019/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ATest {
    @Autowired
    private AreaPartitionMapper areaPartitionMapper;

    @Test
    public void test() {
        List<String> l = new ArrayList<>();
        l.add("PVG");
        List<String> mk = new ArrayList<>();
        mk.add("MU");
        mk.add("MU");
        List<AreaPartition> map = areaPartitionMapper.queryAreaPartitions(l);
        System.out.println(map.get(0).getCountryCode());
        System.out.println(areaPartitionMapper.queryAreaPartition("FRA").getCountryCode());
    }
}

