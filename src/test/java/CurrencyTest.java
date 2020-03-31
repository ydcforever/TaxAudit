import com.atpco.audit.mapper.share.AtpcoTTBS169Mapper;
import com.atpco.audit.mapper.tax3.RoutingTestMapper;
import com.atpco.audit.model.AtpcoTTBS169;
import com.atpco.audit.model.RoutingTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ydc on 2019/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CurrencyTest {
    @Autowired
    private AtpcoTTBS169Mapper atpcoTTBS186Mapper;

    @Autowired
    private RoutingTestMapper routingTestMapper;

    @Test
    public void test() {
        List<AtpcoTTBS169> l = atpcoTTBS186Mapper.query169("00000519");
        System.out.println(l.size());

    }

    @Test
    public void test2() {
        List<RoutingTest> l = routingTestMapper.query(14, "-1", "");
        System.out.println(l.size());
    }
}
