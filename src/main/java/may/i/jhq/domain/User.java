package may.i.jhq.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午4:31
 * @desc The domain of User
 **/
@Data
@JsonIgnoreProperties(value = {"password"})
@Entity
@Table(name = "m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 19950620;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name 用户名
     */
    @Column(name = "name", length = 8)
    private String name;

    /**
     * email 邮箱
     */
    @Column(name = "email", length = 24)
    private String email;

    /**
     * role 用户角色
     */
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="userRoleLink"
        , joinColumns={@JoinColumn(name="userId")}
        , inverseJoinColumns={@JoinColumn(name="roleId")}
    )
    private List<Role> roles;

    /**
     * createTime 用户的创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * password 用户的密码
     */
    @JSONField(serialize = false)
    @Column(name = "password", nullable = true, length = 512)
    private String password;

    /**
     * lastPasswordResetDate 上次更新密码的时间
     */
    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    /**
     * phone 用户注册的手机号
     */
    @Column(name = "phone", length = 15, unique = true)
    private String phone;

}
