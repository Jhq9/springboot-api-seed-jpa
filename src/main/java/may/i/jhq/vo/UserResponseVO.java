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
/**
 * @apiDefine UserResponseVO
 * @apiSuccess {String} data.name 用户名称
 * @apiSuccess {String} data.email 用户的邮箱地址
 * @apiSuccess {String} data.phone 手机号
 * @apiSuccess {Number} data,id 用户id
 * @apiSuccess {Object[]} data.roles 角色集合
 * @apiSuccess {Number} data.roles.id 角色的id
 * @apiSuccess {String} data.roles.name 角色的名字
 * @apiSuccess {Number} data.createTime 用户创建时间
 * @apiSuccess {Number} data.lastPasswordResetDate 用户上次更新密码时间
 */
/**
 * @apiDefine PageUserResponseVO
 * @apiSuccess {String} data.content.name 用户名称
 * @apiSuccess {String} data.content.email 用户的邮箱地址
 * @apiSuccess {String} data.content.phone 手机号
 * @apiSuccess {Number} data.content.id 用户id
 * @apiSuccess {Object[]} data.content.roles 角色集合
 * @apiSuccess {Number} data.content.roles.id 角色的id
 * @apiSuccess {String} data.content.roles.name 角色的名字
 * @apiSuccess {Number} data.content.createTime 用户创建时间
 * @apiSuccess {Number} data.content.lastPasswordResetDate 用户上次更新密码时间
 */
/**
 * @apiDefine UserJsonResponse
 * @apiSuccessExample {json} Success-Response:
 * {
 *      "code":200,
 *      "message":"Success",
 *      "data":{
 *          "id":1,
 *          "name":"May i"
 *          "email":"1044038055@qq.com",
 *          "roles"[{
 *              "id":1,
 *              "name":"ROLE_USER"
 *              }],
 *          "createTime":1516690847,
 *          "lastPasswordResetDate":1516690847,
 *          "phone":"15700084691"
 *     }
 * }
 */
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
