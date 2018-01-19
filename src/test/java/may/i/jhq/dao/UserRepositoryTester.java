package may.i.jhq.dao;

import may.i.jhq.base.Tester;
import may.i.jhq.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * @author jinhuaquan
 * @create 2018-01-19 上午10:10
 * @desc The repository tester of user
 **/
public class UserRepositoryTester extends Tester {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Long id;

    @Before
    public void init() {
        User user = setUser();

        userRepository.save(user);

        Assert.isTrue((id = user.getId()) > 0, "Save failed");
    }


    @Test
    public void testFindOne() {
        User user = userRepository.findOne(id);

        Assert.notNull(user, "Find failed");
    }

    private User setUser() {
        User user = new User();

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        user.setName("jhq");
        user.setEmail("111@qq.com");
        user.setPassword("123");
        user.setCreateTime(Date.from(Instant.now()));
        user.setPhone("110");
        user.setLastPasswordResetDate(Date.from(Instant.now()));

        return user;
    }
}
