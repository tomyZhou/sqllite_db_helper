package cn.heima.db.text;

import java.util.List;

import android.test.AndroidTestCase;
import cn.heima.db.bean.News;
import cn.heima.db.dao.DBHelper;
import cn.heima.db.dao.base.DAO;
import cn.heima.db.dao.base.DAOImpl;

public class DBTest extends AndroidTestCase {
	public void create()
	{
		DBHelper dbHelper=new DBHelper(getContext());
		dbHelper.getWritableDatabase();
	}
	
	public void testInsert()
	{
		News news=new News();
		news.setTitle("标题二");
		news.setSummary("摘要二");
		
		DAO newsDao = new DAOImpl<News>(mContext, News.class);
		List<News> list = newsDao.findAll();
		for(News n:list){
			System.out.println(n.getId()+":"+n.getTitle()+" "+n.getSummary());
		}
		
	}
}
