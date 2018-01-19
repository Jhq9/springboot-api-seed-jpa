package may.i.jhq.service;

import may.i.jhq.config.security.SecurityUser;
import may.i.jhq.domain.User;
import may.i.jhq.vo.UserRequestVO;
import may.i.jhq.vo.UserResponseVO;
import org.springframework.data.domain.Page;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:35
 * @desc The service of user
 **/
public interface UserService {

    /**
     * Save a new user (register)
     * @param requestVO
     * @return
     */
    Long saveUser(UserRequestVO requestVO);

    /**
     * Remove the user by his id
     * @param id
     * @return
     */
    Boolean removeUser(Long id);

    /**
     * Update the user info
     * @param id
     * @param requestVO
     * @return
     */
    Boolean updateUser(Long id, UserRequestVO requestVO);

    /**
     * Get the user info by id
     * @param id
     * @return
     */
    UserResponseVO getUser(Long id);

    /**
     * Use the token to exchange the user's info
     * @param token
     * @return
     */
    SecurityUser getUser(String token);

    /**
     * List all of the user by query params
     * @param requestVO
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<User> listUser(UserRequestVO requestVO, Integer pageNo, Integer pageSize);

    /**
     * User login for exchanging token
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);

    /**
     * Use the old token to exchange new token
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);
}
