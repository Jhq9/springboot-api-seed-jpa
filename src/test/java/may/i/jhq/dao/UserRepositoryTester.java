package may.i.jhq.dao;

import may.i.jhq.base.Tester;
import may.i.jhq.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private EntityManager entityManager;

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

    @Test
    public void testCriteriaQuery() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        List<Predicate> predicateList = new ArrayList<Predicate>();
        predicateList.add(criteriaBuilder.and(criteriaBuilder.like(userRoot.get("name"), "%jhq%")));
        predicateList.add(criteriaBuilder.and(criteriaBuilder.ge(userRoot.get("id"), 0)));

        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

        List<User> users = typedQuery.getResultList();

        Assert.isTrue(users.size() > 0, "Find failed");
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
