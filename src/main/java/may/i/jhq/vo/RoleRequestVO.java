package may.i.jhq.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:34
 * @desc The request VO of role
 **/
@Data
@ApiModel(value = "角色请求类", description = "角色新增与请求")
public class RoleRequestVO {

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名不能为空")
    private String name;


}
