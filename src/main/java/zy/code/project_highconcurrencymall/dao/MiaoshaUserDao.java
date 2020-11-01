package zy.code.project_highconcurrencymall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zy.code.project_highconcurrencymall.domain.MiaoshaUser;


@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public MiaoshaUser findById(@Param("id") long id);
}
