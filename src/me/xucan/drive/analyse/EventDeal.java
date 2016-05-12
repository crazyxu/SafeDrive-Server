package me.xucan.drive.analyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.DatabaseMetaDataUsingInfoSchema;

import me.xucan.drive.bean.DriveEvent;
import me.xucan.drive.bean.DriveRecord;
import me.xucan.drive.util.MessageUtil;
import me.xucan.drive.util.MyBatisUtil;

/**
 * 处理DriveEvent
 * @author xucan
 *
 */
public class EventDeal {
	//事件对安全指数的影响
	private final int SAFETY_INDEX_HIGH = 30;
	private final int SAFETY_INDEX_MED = 20;
	private final int SAFETY_INDEX_LOW = 10;
	
	//该事件所属行车记录
	private DriveRecord record;
	//该记录的其他事件
	private List<DriveEvent> eventList;
	//当前事件
	private DriveEvent thisEvent;

	//sqlSession
	private SqlSession sqlSession;
	
	//行车环境
	private int environment;
	
	private int safetyIndex;
	
	private DriveEvent resEvent;
	
	public EventDeal(DriveEvent event){
		thisEvent = event;
		sqlSession = MyBatisUtil.getSession();
	}
	
	public void start(){
		//提取关于本次记录的全部数据
		record = sqlSession.selectOne(MyBatisUtil.RECORD_GET_ONE, thisEvent.getRecordId());
		if(record == null)
			return;
		safetyIndex = record.getSafetyIndex();
		eventList = sqlSession.selectList(MyBatisUtil.EVENT_SELECT, record.getRecordId());
		if(eventList == null)
			eventList = new ArrayList<>();
		
		//获取当前设置的行车环境
		getEnvironment();
		
		//处理各种事件
		switch(thisEvent.getType()){
			case EventType.EVENT_BRAKES:
				dealBrakes();
				break;
			case EventType.EVENT_FATIGUE:
				dealFatigue();
				break;
			case EventType.EVENT_SKEWING:
				dealSkewing();
				break;
			case EventType.EVENT_ACCELERATION:
				dealAccelerate();
				break;
			case EventType.EVENT_DECELERATION:
				dealDeccelerate();
				break;
		}
		//处理结果
		dealResult();
	}
	
	/**
	 * 获取当前设置的行车环境
	 */
	private void getEnvironment(){
		for(int i = 0; i < eventList.size(); i++){
			int type = eventList.get(i).getType();
			if(type == EventType.ENVIR_NORMAL)
				environment = EventType.ENVIR_NORMAL;
			else if(type == EventType.ENVIR_FOG)
				environment = EventType.ENVIR_FOG;
			else if(type == EventType.ENVIR_JAM)
				environment = EventType.ENVIR_JAM;
			else if(type == EventType.ENVIR_RAIN)
				environment = EventType.ENVIR_RAIN;
		}
	}

	
	/**
	 * 处理急刹事件
	 * 考虑速度，疲惫两个因素
	 */
	private void dealBrakes(){
		//是否存在高速记录
		boolean hasHighSpeed = false;
		boolean fatigue = false;
		
		//结合之前的行车事件
		for(int i = 0; i < eventList.size(); i++){
			DriveEvent event = eventList.get(i);
			switch(event.getType()){
				case EventType.EVENT_ACCELERATION:
					//是否有高速行驶记录
					int speed = Integer.valueOf(event.getExtra());
					if(isHighSpeed(speed))
						hasHighSpeed = true;
					break;
				case EventType.EVENT_FATIGUE:
					fatigue = true;
			}
		}
		if(!hasHighSpeed && !fatigue)
			safetyIndex -= SAFETY_INDEX_LOW;
		else if(!hasHighSpeed && fatigue){
			safetyIndex -= SAFETY_INDEX_MED;
		}else if(hasHighSpeed && !fatigue){
			safetyIndex -= SAFETY_INDEX_MED;
		}else if(hasHighSpeed && fatigue){
			//追尾警告
			safetyIndex -= SAFETY_INDEX_HIGH;
			resEvent = new DriveEvent();
			resEvent.setRecordId(record.getRecordId());
			resEvent.setTime(new Date().getTime());
			resEvent.setType(EventType.WARN_CRASH);
		}
	}
	
	
	
	/**
	 * 处理疲劳事件
	 */
	private void dealFatigue(){
		
	}
	
	/**
	 * 处理偏移事件
	 */
	private void dealSkewing(){
		
	}
	
	/**
	 * 处理加速事件
	 */
	private void dealAccelerate(){
		
	}
	
	/**
	 * 处理减速事件
	 */
	private void dealDeccelerate(){
		
	}
	
	
	/**
	 * 当前速度是否为高速
	 * @param speed
	 * @return
	 */
	private boolean isHighSpeed(int speed){
		switch (environment){
		case EventType.ENVIR_NORMAL:
			if(speed >= 80)
				return true;
			return false;
		case EventType.ENVIR_FOG:
			if(speed >= 40)
				return true;
			return false;
		case EventType.ENVIR_JAM:
			if(speed >= 50)
				return true;
			return false;
		case EventType.ENVIR_RAIN:
			if(speed >= 60)
				return true;
			return false;
		}
		return false;
	}
	
	
	/**
	 * 保存处理结果，并向客户端发送提示
	 */
	private void dealResult(){
		JSONObject json = new JSONObject();
		json.put("safetyIndex", safetyIndex);
		if(resEvent != null){
			json.put("event", resEvent);
			sqlSession.insert(MyBatisUtil.EVENT_INSERT, resEvent);
		}
		//更新safetyIndex
		Map<String, Object> map = new HashMap<>();
		map.put("recordId", record.getRecordId());
		map.put("safetyIndex", safetyIndex);
		sqlSession.update(MyBatisUtil.RECORD_UPDATE_SAFETYINDEX, map);
		MessageUtil.pushMessage(String.valueOf(record.getUserId()), json);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
