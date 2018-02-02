package may.i.jhq.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:29
 * @desc The request vo of user
 **/
/**
 * @apiDefine UserRequestVO
 * @apiParam {String{1..}} name 用户名称
 * @apiParam {String{5..20}} email 用户的邮箱地址
 * @apiParam {String[]} roleNames 用户的角色集合
 * @apiParam {String{6..12}} password 账户的密码
 * @apiParam {String{11}} phone 手机号
 */
@Data
public class UserRequestVO {

    /**
     * User name
     */
    @NotEmpty(message = "用户名不能为空")
    private String name;

    /**
     * email 邮箱
     */
    @Email(message = "邮箱格式有误")
    private String email;

    /**
     * role 用户角色名称
     */
    private List<String> roleNames;

    /**
     * password 用户的密码
     */
    @NotEmpty(message = "用户密码不能为空")
    private String password;

    /**
     * phone 用户注册的手机号
     */
    @NotEmpty(message = "注册手机号不能为空")
    private String phone;

}
