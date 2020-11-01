package zy.code.project_highconcurrencymall.redis;

/**
 * UserKey 创建前缀，可以根据id、name、email等待都可以自己定制
 */
public class UserKey extends BasePrefix{

	private UserKey(String prefix) {
		//使用父类默认构造方法，expireSeconds=0，prefix=参数
		super(prefix);
	}
	public static UserKey getById = new UserKey("id");//UserKey:id 为前缀
	public static UserKey getByName = new UserKey("name");//UserKey:name 为前缀
}
