package may.i.jhq.service.impl;

import may.i.jhq.config.security.GeneratorUserDetailService;
import may.i.jhq.config.security.JwtTokenUtil;
import may.i.jhq.config.security.SecurityUser;
import may.i.jhq.dao.RoleRepository;
import may.i.jhq.dao.UserRepository;
import may.i.jhq.domain.Role;
import may.i.jhq.domain.User;
import may.i.jhq.exception.ServiceException;
import may.i.jhq.service.UserService;
import may.i.jhq.vo.UserRequestVO;
import may.i.jhq.vo.UserResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static may.i.jhq.utils.CglibBeanCopierUtils.copyProperties;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午9:51
 * @desc The interface implement of user
 **/
@Service
public class UserServiceImpl implements UserService{

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GeneratorUserDetailService userDetailService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Long saveUser(UserRequestVO requestVO) {
        Date now = Date.from(Instant.now());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        copyProperties(requestVO, user);

        user.setPassword(encoder.encode(requestVO.getPassword()));
        user.setCreateTime(now);
        user.setLastPasswordResetDate(now);
        user.setRoles(roleRepository.findRolesByNameIn(requestVO.getRoleNames()));

        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Boolean removeUser(Long id) {
        checkArgument(userRepository.findOne(id) != null, "未找到对应的用户");

        userRepository.delete(id);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Boolean updateUser(Long id, UserRequestVO requestVO) {
        User user = userRepository.findOne(id);
        checkArgument(user != null, "未找到对应的用户");
        if (!StringUtils.isEmpty(requestVO.getEmail())) {
            user.setEmail(requestVO.getEmail());
        }
        if (!StringUtils.isEmpty(requestVO.getName())) {
            user.setName(requestVO.getName());
        }
        if (requestVO.getRoleNames() != null && requestVO.getRoleNames().size() > 0) {
            user.setRoles(roleRepository.findRolesByNameIn(requestVO.getRoleNames()));
        }

        userRepository.save(user);

        return Boolean.TRUE;
    }

    @Override
    public UserResponseVO getUser(Long id) {
        User user = userRepository.findOne(id);

        checkArgument(user != null, "未找到对应的用户");
        UserResponseVO userResponseVO = new UserResponseVO();
        copyProperties(user, userResponseVO);

        return userResponseVO;
    }

    @Override
    public SecurityUser getUser(final String token) {
        final String subToken = token.substring(tokenHead.length(),token.length());
        String username = jwtTokenUtil.getUsernameFromToken(subToken);

        SecurityUser user = (SecurityUser) userDetailService.loadUserByUsername(username);

        return user;
    }

    @Override
    public Page<User> listUser(UserRequestVO requestVO, Integer pageNo, Integer pageSize) {
        Specification<User> specification = (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!StringUtils.isEmpty(requestVO.getEmail())) {
                predicates.add(cb.like(root.get("email"), "%" + requestVO.getEmail() + "%"));
            }
            if (!StringUtils.isEmpty(requestVO.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + requestVO.getName() + "%"));
            }
            if (!StringUtils.isEmpty(requestVO.getPhone())) {
                predicates.add(cb.like(root.get("phone"), "%" + requestVO.getPhone() + "%"));
            }
            if (requestVO.getRoleNames() != null && requestVO.getRoleNames().size() > 0) {
                Join<User, Role> join = root.join("roles", JoinType.LEFT);
                predicates.add(join.get("name").in(requestVO.getRoleNames()));
            }
            Predicate[] p = new Predicate[predicates.size()];

            return cb.and(predicates.toArray(p));
        };

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, Sort.Direction.DESC, "id");
        Page<User> users = userRepository.findAll(specification, pageable);

        return users;
    }

    @Override
    public String login(String userName, String password) {
        checkArgument(!StringUtils.isEmpty(userName), "用户名不能为空");
        checkArgument(!StringUtils.isEmpty(password), "密码不能为空");

        /**
         * 根据用户输入的账号及密码生成AuthenticationToken
         */
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            /**
             * 用户名密码登陆效验
             */
            final Authentication authentication = authenticationManager.authenticate(upToken);
            /**
             * 认证成功，将认证信息存入holder中
             */
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            /**
             * jwt工具生成的TOKEN
             */
            final String token = jwtTokenUtil.generateToken(userDetails);

            return token;
        }catch (AuthenticationException e){
            throw new ServiceException("用户不存在或密码错误");
        }
    }

    @Override
    public String refreshToken(String oldToken) {
        final String token = oldToken.substring(tokenHead.length(),oldToken.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);

        try {
            SecurityUser user = (SecurityUser) userDetailService.loadUserByUsername(username);

            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
                return jwtTokenUtil.refreshToken(token);
            }else {
                throw new ServiceException("token刷新失败");
            }
        }catch (AuthenticationException e) {
            throw new ServiceException("认证失败，无法刷新token");
        }
    }
}
