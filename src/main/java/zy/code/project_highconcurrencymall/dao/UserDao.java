package zy.code.project_highconcurrencymall.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zy.code.project_highconcurrencymall.domain.User;

@Mapper
public interface UserDao {

    @Select("select * from t_test where id=#{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into t_test(id,name)values(#{id},#{name})")
    public void insert(User user);

    @Select("select * from t_test where name=#{name}")
    public User getByName(@Param("name")String name);
}
