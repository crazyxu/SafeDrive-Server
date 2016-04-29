package me.xucan.drive.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import me.xucan.drive.bean.DriveRecord;
import me.xucan.drive.bean.User;
import me.xucan.drive.util.JsonUtil;
import me.xucan.drive.util.MyBatisUtil;
import me.xucan.drive.util.TextUtil;
import me.xucan.drive.util.TokenUtil;
import me.xucan.drive.util.UrlConstant;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SqlSession sqlSession;
	private Map<String, Object> map;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	    sqlSession = MyBatisUtil.getSession();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=utf-8");
		String url = request.getRequestURL().toString();
		switch (url) {
		case UrlConstant.URL_USER_REGISTER:
			regiser(request, response);
			break;
		case UrlConstant.URL_USER_LOGIN:
			login(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 注册
	 * @param request
	 * @param response
	 */
	void regiser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		map = new HashMap<>();
		String jsonRecord = request.getParameter("user");
		User user = JSON.parseObject(jsonRecord, User.class);
		if(user != null && TextUtil.isNotEmpty(user.getUserName())){
			sqlSession.insert(MyBatisUtil.USER_INSERT, user);
			if(user.getUserId() != 0){
				//注册成功获取token
				map.put("status", JsonUtil.ERROR_200);
				String tokenJson = TokenUtil.getToken(user);
				JSONObject jsonObject = JSON.parseObject(tokenJson);
				if (jsonObject.getString("code").equals("200")) {
					user.setToken(jsonObject.getJSONObject("result").getString("token"));
					//token保存到数据库
					int res = sqlSession.update(MyBatisUtil.USER_UPDATE_TOKEN, user);
					if( res == 1){
						map.put("status", JsonUtil.ERROR_200);
						map.put("user", user);
					}
				}else{
					map.put("status",JsonUtil.ERROR_500);
					map.put("errorMsg", "get token fail");
				}
				
			}else{
				map.put("status",JsonUtil.ERROR_500);
				map.put("errorMsg", "record create fail");
			}
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "user is empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param response
	 */
	void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		map = new HashMap<>();
		String jsonRecord = request.getParameter("user");
		User loginUser = JSON.parseObject(jsonRecord, User.class);
		if(loginUser != null && TextUtil.isNotEmpty(loginUser.getUserName())){
			User user = sqlSession.selectOne(MyBatisUtil.USER_SELECT, loginUser);
			if(user != null && user.getUserId() != 0){
				map.put("status", JsonUtil.ERROR_200);
				map.put("user", user);
			}else{
				map.put("status",JsonUtil.ERROR_500);
				map.put("errorMsg", "record create fail");
			}
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "params is empty");
		}
		
		response.getWriter().append(JsonUtil.createJson(map));
	}

}
