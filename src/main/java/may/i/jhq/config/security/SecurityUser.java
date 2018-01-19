package may.i.jhq.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import may.i.jhq.domain.Role;
import may.i.jhq.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午5:29
 * @desc The security user
 **/
public class SecurityUser extends User implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        List<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorityList = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(toList());
        authorities.addAll(authorityList);

        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getPhone();
    }

    /**
     * 账户是否未过期
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否激活
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public SecurityUser(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setCreateTime(user.getCreateTime());
            this.setName(user.getName());
            this.setEmail(user.getEmail());
            this.setLastPasswordResetDate(user.getLastPasswordResetDate());
            this.setPhone(user.getPhone());
            this.setPassword(user.getPassword());
            this.setRoles(user.getRoles());
        }
    }
}
