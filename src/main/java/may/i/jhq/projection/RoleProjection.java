package may.i.jhq.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午11:06
 * @desc The Projection of role
 **/
public interface RoleProjection {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.name}")
    String getName();
}
