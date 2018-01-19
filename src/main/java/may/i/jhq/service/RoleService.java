package may.i.jhq.service;

import may.i.jhq.vo.RoleRequestVO;
import may.i.jhq.vo.RoleResponseVO;

import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:44
 * @desc The service of role
 **/
public interface RoleService {

    /**
     * Save a new role
     * @param requestVO
     * @return
     */
    Long saveRole(RoleRequestVO requestVO);

    /**
     * Remove the role by his id
     * @param id
     * @return
     */
    Boolean removeRole(Long id);

    /**
     * Update the role info
     * @param id
     * @param requestVO
     * @return
     */
    boolean updateRole(Long id, RoleRequestVO requestVO);

    /**
     * Get the role info by id
     * @param id
     * @return
     */
    RoleResponseVO getRole(Long id);

    /**
     * List all of the role
     * @return
     */
    List<RoleResponseVO> listRole();
}
