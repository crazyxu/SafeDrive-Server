package me.xucan.drive.servlet;

import java.io.IOException;
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

import me.xucan.drive.analyse.SafetyIndexManager;
import me.xucan.drive.bean.DriveRecord;
import me.xucan.drive.util.JsonUtil;
import me.xucan.drive.util.MyBatisUtil;
import me.xucan.drive.util.TextUtil;
import me.xucan.drive.util.UrlConstant;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet(description = "用于处理行车记录相关请求", urlPatterns = { "/record/*" })
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//driveRecord表操作接口
	private SqlSession sqlSession;
	private Map<String, Object> map;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
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
		String url = request.getRequestURL().toString();
		switch (url) {
		case UrlConstant.URL_DRIVE_START:
			createRecord(request, response);
			break;

		case UrlConstant.URL_DRIVE_STOP:
			updateRecord(request, response);
			break;
			
		case UrlConstant.URL_DRIVE_RECORDS:
			selectRecord(request, response);
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
	 * 新建DriveRecord（开始行驶）
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void createRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		map = new HashMap<>();
		String jsonRecord = request.getParameter("record");
		DriveRecord record = JSON.parseObject(jsonRecord, DriveRecord.class);
		if(record != null && record.getUserId() != 0){
			sqlSession.insert(MyBatisUtil.RECORD_CREATE, record);
			if(record.getRecordId() != 0){
				map.put("status", JsonUtil.ERROR_200);
				map.put("recordId", record.getRecordId());
			}else{
				map.put("status",JsonUtil.ERROR_500);
				map.put("errorMsg", "record create fail");
			}
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "record is empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	
	/**
	 * 更新DriveRecord（行驶结束）
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		map = new HashMap<>();
		String jsonRecord = request.getParameter("record");
		DriveRecord record = JSON.parseObject(jsonRecord, DriveRecord.class);
		if(record != null && record.getUserId() != 0){
			int res = sqlSession.insert(MyBatisUtil.RECORD_UPDATE, record);
			if(res == 1){
				map.put("status", JsonUtil.ERROR_200);
				map.put("safetyIndex", SafetyIndexManager.getSafetyIndex(record.getRecordId()));
			}else{
				map.put("status",JsonUtil.ERROR_500);
				map.put("errorMsg", "record update fail");
			}
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "record is empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	
	/**
	 * 查询某用户的DriveRecord
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void selectRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		map = new HashMap<>();
		String userIdStr = request.getParameter("userId");
		String fromNumStr = request.getParameter("fromNum");
		String toNumStr = request.getParameter("toNum");
		if(TextUtil.isNotEmpty(userIdStr, fromNumStr, toNumStr)){
			map.put("fromNum", Integer.valueOf(fromNumStr));
			map.put("toNum", Integer.valueOf(toNumStr));
			map.put("userId", Integer.valueOf(userIdStr));
			List<DriveRecord> records = sqlSession.selectList(MyBatisUtil.RECORD_SELECT, map);
			map.put("status", JsonUtil.ERROR_200);
			map.put("records", records);
		}else{
			map.put("status",JsonUtil.ERROR_PARAM_NULL);
			map.put("errorMsg", "params has empty");
		}
		response.getWriter().append(JsonUtil.createJson(map));
	}
	

}
