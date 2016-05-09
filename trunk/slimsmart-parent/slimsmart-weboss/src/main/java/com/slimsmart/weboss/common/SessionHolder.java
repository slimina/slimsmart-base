package com.slimsmart.weboss.common;

/**
 * 便于使用静态方法后端获取sesion
 */
public class SessionHolder {
	
	private static ThreadLocal<SessionData> sessionThreadLocalHolder = new ThreadLocal<SessionData>();  
    
    public static void set(SessionData sessionUser){  
    	sessionThreadLocalHolder.set(sessionUser);  
    }  
      
    public static SessionData get(){  
        return  sessionThreadLocalHolder.get();  
    }   
    
    public static void remove(){  
        sessionThreadLocalHolder.remove(); 
    }   
}
