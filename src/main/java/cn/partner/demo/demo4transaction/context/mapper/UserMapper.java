package cn.partner.demo.demo4transaction.context.mapper;

import cn.partner.demo.demo4transaction.context.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Yonsin on 17/12/22.
 */
@Mapper
public interface UserMapper {

    void createTableIfNotExist();

    void save(UserEntity user);

    void update(UserEntity user);

    UserEntity findById(int id);
}
