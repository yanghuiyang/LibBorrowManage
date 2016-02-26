package com.yang.bishe.Interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.User;
import com.yang.bishe.util.SecurityUtil;
public class CommonInterceptor  extends HandlerInterceptorAdapter{
	@Override   
    public boolean preHandle(HttpServletRequest request,   
        HttpServletResponse response, Object handler) throws Exception {  
        Object obj = request.getSession().getAttribute("sessionInfo");  
        String url=request.getRequestURI();
        String[] noFilters = new String[] { "index.html", "index",  
        		"home2", "home","loginOut"};  
        for(String s : noFilters){
        	if(url.endsWith(s)){
        		 return super.preHandle(request, response, handler);  
        	}
        }
        if(obj==null){  
            request.getRequestDispatcher("/error/noSession.jsp").forward(request, response);   
            return false;  
        } 
        else{  
        	if(havePermission(url,request)){
        		return super.preHandle(request, response, handler);  
        	}
         //   return super.preHandle(request, response, handler);
        	 request.getRequestDispatcher("/error/noSercurity.jsp").forward(request, response);   
             return false;  
        }  
    }

	private boolean havePermission(String url,HttpServletRequest request) {
		// TODO Auto-generated method stub
		User sessionInfo = (User) request.getSession().getAttribute("sessionInfo"); 
		//	List<Operation> operations = new ArrayList<Operation>();
			if(sessionInfo!=null){	
					Set<Operation> operations=sessionInfo.getRole().getOpeations();
					for (Operation op : operations) {
						if (StringUtils.equals(op.getUrl(), url)) {// 如果有相同的，则代表当前用户可以访问这个资源
							return true;
						}
					}
			}
					return false;
				}
	}  
