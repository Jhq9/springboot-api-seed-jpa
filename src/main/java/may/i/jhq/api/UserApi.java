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

    @PostMapping("")
    public Result register(@Valid @RequestBody UserRequestVO requestVO) {
        LOGGER.info("API 调用 ： 用户注册(保存)");

        return ResultGenerator.genSuccessResult(userService.saveUser(requestVO));
    }

    @DeleteMapping("/{id}")
    public Result removeUser(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 删除某个用户");

        return ResultGenerator.genSuccessResult(userService.removeUser(id));
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Long id, @RequestBody UserRequestVO requestVO) {
        LOGGER.info("API 调用 ： 用户信息更新");

        return ResultGenerator.genSuccessResult(userService.updateUser(id, requestVO));
    }


    @GetMapping("/{id}")
    public Result getUser(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 查询用户的个人信息");

        return ResultGenerator.genSuccessResult(userService.getUser(id));
    }

    @GetMapping("")
    public Result getUser(@RequestHeader("Authorization") final String token) {
        LOGGER.info("API 调用 ： token换取个人用户信息");

        return ResultGenerator.genSuccessResult(userService.getUser(token));
    }

    @PostMapping("/actions/query")
    public Result listUser(@RequestBody UserRequestVO requestVO,
                           @RequestParam(defaultValue = "1", required = false) Integer pageNo,
                           @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        LOGGER.info("API 调用 ： 条件查询用户");

        return ResultGenerator.genSuccessResult(userService.listUser(requestVO, pageNo, pageSize));
    }

    @GetMapping("/actions/login")
    public Result login(@RequestHeader String userName, @RequestHeader String password) {
        LOGGER.info("API 调用 ： 用户登录");

        return ResultGenerator.genSuccessResult(userService.login(userName, password));
    }

    @GetMapping("/actions/refresh")
    public Result refresh(@RequestHeader("Authorization") final String oldToken) {
        LOGGER.info("API 调用 ： token刷新");

        return ResultGenerator.genSuccessResult(userService.refreshToken(oldToken));
    }
}
