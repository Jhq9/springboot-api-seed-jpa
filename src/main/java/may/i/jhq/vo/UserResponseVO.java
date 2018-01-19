package may.i.jhq.vo;

import lombok.Data;
import may.i.jhq.domain.Role;

import java.util.Date;
import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:33
 * @desc The response VO of user
 **/
@Data
public class UserResponseVO {

    private Long id;

    /**
     * name 用户名
     */
    private String name;

    /**
     * email 邮箱
     */
    private String email;

    /**
     * role 用户角色
     */
    private List<Role> roles;

    /**
     * createTime 用户的创建时间
     */
    private Date createTime;

    /**
     * lastPasswordResetDate 上次更新密码的时间
     */
    private Date lastPasswordResetDate;

    /**
     * phone 用户注册的手机号
     */
    private String phone;

}
