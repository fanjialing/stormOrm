package org.storm.orm.bean;
/**
 * 管理配置信息
 * @author Administrator
 *
 */
public class Configuration {
	/**
	 *  jdbc url
	 */
	private String url;
	/**
	 * 驱动类
	 */
	private String	driverClass;
	/**
	 * 项目源码路径
	 */
	private String	srcPath;
	/**
	 * 正在使用哪个数据库
	 */
	private String	usingDB;
	/**
	 * 扫描生成java的包
	 */
	private String	poPackage;
	/**
	 * 用户名
	 */
	private String	username;
	/**
	 * 用户密码
	 */
	private String	password;
	
	
	
	public Configuration() {
		super();
	}
	public Configuration(String url, String driverClass, String srcPath,
			String usingDB, String poPackage, String username, String password) {
		super();
		this.url = url;
		this.driverClass = driverClass;
		this.srcPath = srcPath;
		this.usingDB = usingDB;
		this.poPackage = poPackage;
		this.username = username;
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}
	public String getPoPackage() {
		return poPackage;
	}
	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
