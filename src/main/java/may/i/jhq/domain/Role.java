package may.i.jhq.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午4:33
 * @desc The domain of role
 **/
@Data
@Entity
@Table(name = "m_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name 角色名称
     */
    @Column(name = "name", length = 16)
    private String name;
}
