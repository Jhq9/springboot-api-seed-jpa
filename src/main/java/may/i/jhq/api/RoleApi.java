package may.i.jhq.api;

import may.i.jhq.core.Result;
import may.i.jhq.core.ResultGenerator;
import may.i.jhq.service.RoleService;
import may.i.jhq.vo.RoleRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午11:14
 * @desc The api of role
 **/
@RestController
@RequestMapping(value = "/api/roles")
public class RoleApi {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleApi.class);

    @Autowired
    private RoleService roleService;

    /**
     * @api {POST} /api/roles 新增新角色
     * @apiGroup Role
     * @apiParam  {json} requestVO 角色请求信息
     * @apiParam  {String} requestVO.name 新增的角色名称
     * @apiPermission ROLE_USER 普通用户
     * @apiSuccess {Number} data 所新保存的角色的id值
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonIdResponse
     */
    @PostMapping("")
    public Result saveRole(@Valid @RequestBody RoleRequestVO requestVO) {
        LOGGER.info("API 调用 ： 新增新角色");

        return ResultGenerator.genSuccessResult(roleService.saveRole(requestVO));
    }

    /**
     * @api {DELETE} /api/roles/{id} 删除角色
     * @apiGroup Role
     * @apiParam  {Number} id 角色id
     * @apiPermission ROLE_ADMIN 管理员
     * @apiSuccess {Boolean} data 返回布尔值,true表示删除成功,false表示删除失败
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonBoolResponse
     */
    @DeleteMapping("/{id}")
    public Result removeRole(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 删除角色");

        return ResultGenerator.genSuccessResult(roleService.removeRole(id));
    }

    /**
     * @api {PUT} /api/roles/{id} 更新角色信息
     * @apiGroup Role
     * @apiParam  {Number} id 角色id
     * @apiPermission ROLE_ADMIN 管理员
     * @apiSuccess {Boolean} data 返回布尔值,true表示更新成功,false表示更新失败
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonBoolResponse
     */
    @PutMapping("/{id}")
    public Result updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequestVO requestVO) {
        LOGGER.info("API 调用 ： 更新角色信息");

        return ResultGenerator.genSuccessResult(roleService.updateRole(id, requestVO));
    }

    /**
     * @api {GET} /api/roles/{id} 获取角色信息
     * @apiGroup Role
     * @apiParam  {Number} id 角色id
     * @apiPermission ROLE_ADMIN 管理员
     * @apiSuccess {Object} data 返回该角色的信息
     * @apiSuccess {Number} data.id 该角色的id
     * @apiSuccess {String} data.name 该角色的名称
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiSuccessExample {json} 示例返回结果:
     * HTTP/1.1 200 OK
     * {
     *      "code":200,
     *      "message":"Success",
     *      "data":{
     *          "name":"ROLE_USER",
     *          "id":1
     *      }
     * }
     */
    @GetMapping("/{id}")
    public Result getRole(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 获取角色");

        return ResultGenerator.genSuccessResult(roleService.getRole(id));
    }

    /**
     * @api {GET} /api/roles 获取所有角色信息
     * @apiGroup Role
     * @apiPermission ROLE_ADMIN 管理员
     * @apiSuccess {Object[]} data 返回所有角色的信息
     * @apiSuccess {Number} data.id 角色的id
     * @apiSuccess {String} data.name 角色的名称
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiSuccessExample {json} 示例返回结果:
     * HTTP/1.1 200 OK
     * {
     *      "code":200,
     *      "message":"Success",
     *      "data":[{
     *          "name":"ROLE_USER",
     *          "id":1
     *      }]
     * }
     */
    @GetMapping("")
    public Result listRole() {
        LOGGER.info("API 调用 ： 罗列角色");

        return ResultGenerator.genSuccessResult(roleService.listRole());
    }
}
