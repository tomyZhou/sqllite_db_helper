package cn.heima.db.bean;

import cn.heima.db.dao.DBHelper;
import cn.heima.db.dao.annontation.Column;
import cn.heima.db.dao.annontation.ID;
import cn.heima.db.dao.annontation.TableName;

@TableName(DBHelper.TABLE_NEWS_NAME)
public class News {
	@ID(autoincrement=true)
	@Column(DBHelper.TABLE_ID)
	private String id;
	@Column(DBHelper.TABLE_NEWS_TITLE)
	private String title;
	@Column(DBHelper.TABLE_NEWS_SUMMARY)
	private String summary;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
