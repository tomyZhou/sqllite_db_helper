package cn.heima.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
	private static String DBNAME = "itcast.db";
	private static int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	public static final String TABLE_ID = "_id";// 主键对应信息

	// 新闻
	public static final String TABLE_NEWS_NAME = "news";// 新闻表名
	public static final String TABLE_NEWS_ID = "_id";// 新闻主键
	public static final String TABLE_NEWS_TITLE = "title";// 新闻主键
	public static final String TABLE_NEWS_SUMMARY = "summary";// 新闻主键
	
	public static final String TABLE_BOOK = "news";// 新闻表名
	public static final String TABLE_BOOK_NAME = "name";// 新闻主键
	public static final String TABLE_BOOK_PRICE = "price";// 新闻主键

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NEWS_NAME + " (" + //
				TABLE_ID + " integer primary key autoincrement, " + //
				TABLE_NEWS_TITLE + " varchar(50), " + //
				TABLE_NEWS_SUMMARY + " VARCHAR(200))"//
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
