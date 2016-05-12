package me.xucan.drive.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;

import me.xucan.drive.analyse.EventDeal;
import me.xucan.drive.analyse.SafetyIndexManager;
import me.xucan.drive.bean.DriveEvent;
import me.xucan.drive.bean.DriveRecord;
import me.xucan.drive.util.JsonUtil;
import me.xucan.drive.util.MessageUtil;
import me.xucan.drive.util.MyBatisUtil;
import me.xucan.drive.util.TextUtil;
import me.xucan.drive.util.UrlConstant;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet(description = "行车过程中发送事件", urlPatterns = { "/event/*" })
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SqlSession sqlSession;
	private Map<String, Object> map;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
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
		case UrlConstant.URL_EVENT_SEND:
			saveEvent(request, response);
			break;

		case UrlConstant.URL_EVENT_GET:
			getEvents(request, response);
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
	 * 发送事件
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		map = new HashMap<>();
		String jsonRecord = request.getParameter("event");
		String userId = request.getParameter("userId");
		DriveEvent event = JSON.parseObject(jsonRecord, DriveEvent.class);
		if(event != null && event.getRecordId() != 0){
			int res = sqlSession.insert(MyBatisUtil.EVENT_INSERT, event);
			if(res == 1){
				map.put("status", JsonUtil.ERROR_200);
				//处理事件
				new EventDeal(event).start();
			}else{
				map.put("status",JsonUtil.ERROR_500);
				map.put("errorMsg", "data save fail");
			}
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "record is empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	
	
	/**
	 * 获取Event
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		map = new HashMap<>();
		String recordIdStr = request.getParameter("recordId");
		if(TextUtil.isNotEmpty(recordIdStr)){
			int recordId = Integer.valueOf(recordIdStr);
			List<DriveEvent> events = sqlSession.selectList(MyBatisUtil.EVENT_SELECT, recordId);
			map.put("status", JsonUtil.ERROR_200);
			map.put("events", events);
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "params has empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	

}
