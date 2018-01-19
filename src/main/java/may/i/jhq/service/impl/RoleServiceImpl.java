package may.i.jhq.service.impl;

import may.i.jhq.dao.RoleRepository;
import may.i.jhq.dao.UserRepository;
import may.i.jhq.domain.Role;
import may.i.jhq.domain.User;
import may.i.jhq.projection.RoleProjection;
import may.i.jhq.service.RoleService;
import may.i.jhq.vo.RoleRequestVO;
import may.i.jhq.vo.RoleResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午10:53
 * @desc The service implement of role
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Long saveRole(RoleRequestVO requestVO) {
        checkArgument(roleRepository.findByName(requestVO.getName()) == null, "已存在此角色");
        Role role = new Role();
        role.setName(requestVO.getName());

        roleRepository.save(role);
        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Boolean removeRole(Long id) {
        Role role = roleRepository.findOne(id);

        checkArgument(roleRepository.findOne(id) != null, "未找到对应的角色");
        List<User> users = userRepository.findUsersByRolesContains(role);

        users.stream().forEach(user -> {
            List<Role> roles = user.getRoles();

            roles.remove(role);

            user.setRoles(roles);
        });

        userRepository.save(users);

        roleRepository.delete(role);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public boolean updateRole(Long id, RoleRequestVO requestVO) {
        checkArgument(!StringUtils.isEmpty(requestVO.getName()), "角色名不能为空");
        checkArgument(roleRepository.findByName(requestVO.getName()) == null, "已存在此角色");
        Role role = roleRepository.findOne(id);

        checkArgument(role != null, "未找到对应的角色");

        role.setName(requestVO.getName());
        roleRepository.save(role);
        return Boolean.TRUE;
    }

    @Override
    public RoleResponseVO getRole(Long id) {
        Role role = roleRepository.findOne(id);
        checkArgument(roleRepository.findOne(id) != null, "未找到对应的角色");

        return new RoleResponseVO(role.getId(), role.getName());
    }

    @Override
    public List<RoleResponseVO> listRole() {
        List<RoleProjection> roles = roleRepository.findAllProjection();
        List<RoleResponseVO> responseVOs =  roles.stream().map(role -> new RoleResponseVO(role.getId(), role.getName())).collect(toList());

        return responseVOs;
    }
}
