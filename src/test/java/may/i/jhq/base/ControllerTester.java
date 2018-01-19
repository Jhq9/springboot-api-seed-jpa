package may.i.jhq.base;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author jinhuaquan
 * @create 2018-01-19 上午10:08
 * @desc The controller base tester
 **/
@WebAppConfiguration
public class ControllerTester extends Tester{

    @Autowired
    public WebApplicationContext context;

    public MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
