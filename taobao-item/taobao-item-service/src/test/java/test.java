import com.yhq.dao.SpuMapper;
import com.yhq.entity.Spu;
import com.yhq.entity.SpuBo;
import com.yhq.service.GoodsService;
import com.yhq.taobaoItemService;
import com.yhq.vo.PageResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 19:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = taobaoItemService.class)
public class test {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    SpuMapper mapper;

    @Test

    public void test(){
        List<Spu> spus = mapper.selectSpu(true, "");
        System.out.println(spus);

    }
}
