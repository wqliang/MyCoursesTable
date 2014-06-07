package model;


public class UserInfo {
	
	private String id;
	private String password;
	private static class SingletonHolder {
		private static UserInfo instance = new UserInfo();  
    }  
  
    /** 
     * 私有的构造函数 
     */  
    private UserInfo() {  
    	
    }  
  
    public static UserInfo getInstance() {  
        return SingletonHolder.instance;  
    }  
  
    public void method() {  
        System.out.println("SingletonInner");  
    }  
	
    public String getId() {
		return id;
	}
    public void setId(String id) {
		this.id = id;
	}
    public String getPassword() {
		return password;
	}
    public void setPassword(String password) {
		this.password = password;
	}
    private UserInfo(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	
}
