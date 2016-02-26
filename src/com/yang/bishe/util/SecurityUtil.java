package com.yang.bishe.util;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.User;
public class SecurityUtil {
	private HttpSession session;

	public SecurityUtil(HttpSession session) {
		this.session = session;
	}

	/**
	 * 鍒ゆ柇褰撳墠鐢ㄦ埛鏄惁鍙互璁块棶鏌愯祫婧�
	 * 
	 * @param url
	 *            璧勬簮鍦板潃
	 * @return
	 */
	public boolean havePermission(String url) {
		User sessionInfo = (User) session.getAttribute("sessionInfo");
	//	List<Operation> operations = new ArrayList<Operation>();
		if(sessionInfo!=null){	
				Set<Operation> operations=sessionInfo.getRole().getOpeations();
				for (Operation op : operations) {
					if (StringUtils.equals(op.getUrl(), url)) {// 濡傛灉鏈夌浉鍚岀殑锛屽垯浠ｈ〃褰撳墠鐢ㄦ埛鍙互璁块棶杩欎釜璧勬簮
						return true;
					}
				}
		}
				return false;
			}
}
