package may.i.jhq.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:29
 * @desc The request vo of user
 **/
@Data
@ApiModel(value = "用户请求类", description = "用户注册、更新及查询")
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
