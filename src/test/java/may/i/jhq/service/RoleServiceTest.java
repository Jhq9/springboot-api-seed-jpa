package may.i.jhq.service;

import may.i.jhq.dao.RoleRepository;
import may.i.jhq.dao.UserRepository;
import may.i.jhq.domain.Role;
import may.i.jhq.domain.User;
import may.i.jhq.projection.RoleProjection;
import may.i.jhq.service.impl.RoleServiceImpl;
import may.i.jhq.vo.RoleRequestVO;
import may.i.jhq.vo.RoleResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    //用于定义被Mock的组件
    @Mock
    private RoleRepository roleRepository;

    //用于定义被Mock的组件
    @Mock
    private UserRepository userRepository;

    //用于定义待测试的组件
    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleRequestVO requestVO;

    private Role role;

    List<User> users = mock(List.class);

    /**
     * Set up and set mocks
     */
    @Before
    public void setup() {
        //用于初始化@Mock注解修饰的组件
        MockitoAnnotations.initMocks(this);

        requestVO = new RoleRequestVO();
        requestVO.setName("ROLE_ADMIN");

        role = new Role();
        role.setName("ROLE_ADMIN");
        role.setId(new Random().nextLong());
    }

    /**
     * Mock the logic of save a new role
     * @throws Exception
     */
    @Test
    public void saveRole() throws Exception {
        doAnswer(invocationOnMock -> role).when(roleRepository).save(any(Role.class));

        Long roleId = roleService.saveRole(requestVO);
        Assert.assertEquals(roleId, role.getId());

        verify(roleRepository).save(any(Role.class));
    }

    /**
     * Mock the logic of remove the role
     * @throws Exception
     */
    @Test
    public void removeRole() throws Exception {

        doReturn(role).when(roleRepository).findOne(anyLong());
        doAnswer(invocationOnMock -> users).when(userRepository).findUsersByRolesContains(any(Role.class));
        doAnswer(invocationOnMock -> users).when(userRepository).save(any(List.class));
        doNothing().when(roleRepository).delete(any(Role.class));

        boolean result = roleService.removeRole(anyLong());

        Assert.assertTrue("Remove role failed", result);
    }

    /**
     * Mock the logic of update role
     * @throws Exception
     */
    @Test
    public void updateRole() throws Exception {

        doReturn(null).when(roleRepository).findByName(anyString());
        doReturn(role).when(roleRepository).findOne(anyLong());
        doReturn(role).when(roleRepository).save(any(Role.class));

        boolean result = roleService.updateRole(anyLong(), requestVO);

        Assert.assertTrue("Update role failed", result);
    }

    /**
     * Mock the logic of get role
     * @throws Exception
     */
    @Test
    public void getRole() throws Exception {
        doReturn(role).when(roleRepository).findOne(anyLong());

        RoleResponseVO responseVO = roleService.getRole(anyLong());

        Assert.assertNotNull("Get role failed", responseVO);

        verify(roleRepository).findOne(anyLong());
    }

    /**
     * Mock the logic of list all roles
     * @throws Exception
     */
    @Test
    public void listRole() throws Exception {

        doReturn(mockRoleProjection()).when(roleRepository).findAllProjection();

        List<RoleResponseVO> responseVOs = roleService.listRole();

        Assert.assertNotNull(responseVOs);

        verify(roleRepository).findAllProjection();
    }

    /**
     * Mock Role Projection
     * @return
     */
    public static List<RoleProjection> mockRoleProjection() {
        List<RoleProjection> roleProjections = new ArrayList<RoleProjection>();
        roleProjections.add(new RoleProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "ROLE_ADMIN";
            }
        });
        return roleProjections;
    }
}