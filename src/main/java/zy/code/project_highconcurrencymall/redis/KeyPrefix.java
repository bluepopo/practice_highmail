package zy.code.project_highconcurrencymall.redis;

/**
 * KeyPrefix key前缀的生成与获取
 */
public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
