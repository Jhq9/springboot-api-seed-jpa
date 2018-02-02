package may.i.jhq.api;

import may.i.jhq.core.Result;
import may.i.jhq.core.ResultGenerator;
import may.i.jhq.service.UserService;
import may.i.jhq.vo.UserRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author jinhuaquan
 * @create 2018-01-19 上午9:02
 * @desc The api of user
 **/
@RestController
@RequestMapping(value = "/api/users")
public class UserApi {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    /**
     * @apiDefine JsonTokenResponse
     * @apiSuccessExample {json} Success-Response:
     * {
     *      "code": 200,
     *      "data": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTcwMDA4NDY5MSIsImNyZWF0ZWQiOjE1MTY3MDE5ODMxMTAsImV4cCI6MTUxNzMwNjc4M30.NessiN8xyvUBpKfhHrtJ9FyKDBQe1YUa1Vcqp8b9hdrtqdHxvUdgHtGN8PkH_wrf-9n44HZ-dzVkBNza2r8PsQ",
     *"message": "SUCCESS"
     *  }
     */

    /**
     * @api {POST} /api/users 新增新用户
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiSuccess {Number} data 所新保存的角色的id值
     * @apiUse UserRequestVO
     * @apiUse NormalResponse
     * @apiUse JsonIdResponse
     */
    @PostMapping("")
    public Result register(@Valid @RequestBody UserRequestVO requestVO) {
        LOGGER.info("API 调用 ： 用户注册(保存)");

        return ResultGenerator.genSuccessResult(userService.saveUser(requestVO));
    }

    /**
     * @api {DELETE} /api/users/{id} 删除用户
     * @apiGroup User
     * @apiPermission ROLE_ADMIN 管理员
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonBoolResponse
     */
    @DeleteMapping("/{id}")
    public Result removeUser(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 删除某个用户");

        return ResultGenerator.genSuccessResult(userService.removeUser(id));
    }

    /**
     * @api {PUT} /api/users/{id} 更新用户信息
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonBoolResponse
     */
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Long id, @RequestBody UserRequestVO requestVO) {
        LOGGER.info("API 调用 ： 用户信息更新");

        return ResultGenerator.genSuccessResult(userService.updateUser(id, requestVO));
    }

    /**
     * @api {GET} /api/users/{id} 根据id获取用户信息
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse UserResponseVO
     * @apiUse UserJsonResponse
     * @apiSuccess {Object} data
     */
    @GetMapping("/{id}")
    public Result getUser(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 查询用户的个人信息");

        return ResultGenerator.genSuccessResult(userService.getUser(id));
    }

    /**
     * @api {GET} /api/users 根据token获取用户信息
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiSuccess {Object} data
     * @apiUse UserResponseVO
     * @apiUse UserJsonResponse
     */
    @GetMapping("")
    public Result getUser(@RequestHeader("Authorization") final String token) {
        LOGGER.info("API 调用 ： token换取个人用户信息");

        return ResultGenerator.genSuccessResult(userService.getUser(token));
    }

    /**
     * @api {POST} /api/users/actions/query 条件查询用户列表
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiParam {Number} pageNo="1" 页码；路径?后面
     * @apiParam {Number} pageSize="10" 每页大小；路径?后面
     * @apiUse UserRequestVO
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse PageUserResponseVO
     * @apiUse PageResponseParam
     * @apiUse JsonPageResponse
     */
    @PostMapping("/actions/query")
    public Result listUser(@RequestBody UserRequestVO requestVO,
                           @RequestParam(defaultValue = "1", required = false) Integer pageNo,
                           @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        LOGGER.info("API 调用 ： 条件查询用户");

        return ResultGenerator.genSuccessResult(userService.listUser(requestVO, pageNo, pageSize));
    }

    /**
     * @api {POST} /api/users/actions/login 用户登录
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiHeader {String} userName 登录名
     * @apiHeader {String} password 用户密码
     * @apiUse NormalResponse
     * @apiUse JsonTokenResponse
     * @apiSuccess {String} data 用户的token
     */
    @GetMapping("/actions/login")
    public Result login(@RequestHeader String userName, @RequestHeader String password) {
        LOGGER.info("API 调用 ： 用户登录");

        return ResultGenerator.genSuccessResult(userService.login(userName, password));
    }

    /**
     * @api {POST} /api/users/actions/refresh 刷新token
     * @apiGroup User
     * @apiPermission ROLE_USER 普通用户
     * @apiUse NormalResponse
     * @apiUse JsonHeader
     * @apiUse JsonTokenResponse
     * @apiSuccess {String} data 用户的token
     */
    @GetMapping("/actions/refresh")
    public Result refresh(@RequestHeader("Authorization") final String oldToken) {
        LOGGER.info("API 调用 ： token刷新");

        return ResultGenerator.genSuccessResult(userService.refreshToken(oldToken));
    }
}
