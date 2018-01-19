package may.i.jhq.base;

import may.i.jhq.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jinhuaquan
 * @create 2018-01-19 上午10:01
 * @desc The base abstract tester
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Transactional
@Rollback
//过滤掉身份认证
@ActiveProfiles("test")
public abstract class Tester {



}
