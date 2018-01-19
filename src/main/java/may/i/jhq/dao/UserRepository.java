package may.i.jhq.dao;

import may.i.jhq.domain.Role;
import may.i.jhq.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午5:28
 * @desc The repository of user
 **/
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Find the user by unique phone
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * Find all the users who have the same role
     * @param role
     * @return
     */
    List<User> findUsersByRolesContains(Role role);
}
