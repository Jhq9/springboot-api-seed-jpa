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

    @PostMapping("")
    public Result saveRole(@Valid @RequestBody RoleRequestVO requestVO) {
        LOGGER.info("API 调用 ： 新增新角色");

        return ResultGenerator.genSuccessResult(roleService.saveRole(requestVO));
    }

    @DeleteMapping("/{id}")
    public Result removeRole(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 删除角色");

        return ResultGenerator.genSuccessResult(roleService.removeRole(id));
    }

    /**
     * @param id
     * @param requestVO
     * @return
     */
    @PutMapping("/{id}")
    public Result updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequestVO requestVO) {
        LOGGER.info("API 调用 ： 更新角色信息");

        return ResultGenerator.genSuccessResult(roleService.updateRole(id, requestVO));
    }

    @GetMapping("/{id}")
    public Result getRole(@PathVariable Long id) {
        LOGGER.info("API 调用 ： 获取角色");

        return ResultGenerator.genSuccessResult(roleService.getRole(id));
    }

    @GetMapping("")
    public Result listRole() {
        LOGGER.info("API 调用 ： 罗列角色");

        return ResultGenerator.genSuccessResult(roleService.listRole());
    }
}
