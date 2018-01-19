package may.i.jhq.dao;

import may.i.jhq.domain.Role;
import may.i.jhq.projection.RoleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:49
 * @desc The repository of role
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find the role by name
     * @param name
     * @return
     */
    Role findByName(String name);

    /**
     * Find all the role by role names
     * @param names
     * @return
     */
    List<Role> findRolesByNameIn(List<String> names);

    /**
     * Find all role and set value to role projection
     * @return
     */
    @Query(value = "select r.id as id, r.name as name from Role as r")
    List<RoleProjection> findAllProjection();
}
