package cn.heima.db.bean;

import cn.heima.db.dao.DBHelper;
import cn.heima.db.dao.annontation.Column;
import cn.heima.db.dao.annontation.ID;
import cn.heima.db.dao.annontation.TableName;

@TableName(DBHelper.TABLE_BOOK)
public class Book {
	@ID(autoincrement=true)
	@Column(DBHelper.TABLE_ID)
	private int id;
	@Column(DBHelper.TABLE_BOOK_NAME)
	private String name;
	@Column(DBHelper.TABLE_BOOK_PRICE)
	private String price;
}
