package may.i.jhq.config.security;

import may.i.jhq.dao.UserRepository;
import may.i.jhq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午5:32
 * @desc The service of load user by user name
 **/
@Component
public class GeneratorUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        SecurityUser securityUser = new SecurityUser(user);

        return securityUser;
    }
}
