package cn.heima.db.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.heima.db.dao.DBHelper;
import cn.heima.db.dao.annontation.Column;
import cn.heima.db.dao.annontation.ID;
import cn.heima.db.dao.annontation.TableName;

public  class DAOImpl<T> implements DAO<T> {
	private static final String TAG = "DAOImpl";
	protected Context context;
	protected DBHelper dbHelper;
	protected SQLiteDatabase database;
	private Class clazz;

	public DAOImpl(Context context,Class clazz) {
		super();
		this.context = context;
		this.clazz = clazz;
		dbHelper = new DBHelper(context);
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public int delete(Serializable id) {
		// 问题一：表名获取
		int delete = database.delete(getTableName(), DBHelper.TABLE_ID + "=?", new String[] { id.toString() });
		return delete;
	}

	@Override
	public List<T> findAll() {
		Cursor query = null;
		List<T> result = new ArrayList<T>();
		// 问题一：表名获取
		try {
			query = database.query(getTableName(), null, null, null, null, null, null);
			while (query.moveToNext()) {
				// 问题二：创建一个T的实例
				T t = getInstance();
				// 问题三：将数据库中获取到Cursor里信息封装T的实例
				fillInstance(query, t);
				result.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (query != null)
				query.close();
		}

		// T t =new T();
		return result;
	}

	/**
	 * 将Cursor信息传递到T
	 * 
	 * @param query
	 * @param t
	 */
	private void fillInstance(Cursor query, T t) {
		// News news=new News();
		//		
		// news.getTitle()//获取到DBHelper.TABLE_NEWS_TITLE
		//		
		// int columnIndex = query.getColumnIndex(DBHelper.TABLE_NEWS_TITLE);
		// String title = query.getString(columnIndex);

		Field[] declaredFields = t.getClass().getDeclaredFields();
		for (Field item : declaredFields) {
			// 暴力反射
			item.setAccessible(true);

			Column annotation = item.getAnnotation(Column.class);
			if (annotation != null) {
				String key = annotation.value();
				System.out.println(key);
				int columnIndex = query.getColumnIndex(key);
				String info = query.getString(columnIndex);
				System.out.println(info);

				try {
					item.set(t, info);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public long insert(T t) {
		ContentValues values = new ContentValues();
		// 问题一：将T的实例里面封装的信息传递ContentValues
		// values.put(DBHelper.TABLE_NEWS_TITLE, news.getTitle());
		fillContentValues(t, values);

		// 问题二：表名获取
		long insert = database.insert(getTableName(), null, values);
		return insert;
	}

	/**
	 * 将T的实例里面封装的信息传递ContentValues
	 * 
	 * @param t
	 * @param values
	 */
	private void fillContentValues(T t, ContentValues values) {
		
		Field[] declaredFields = t.getClass().getDeclaredFields();
		for (Field item : declaredFields) {
			item.setAccessible(true);

			// values.put(DBHelper.TABLE_NEWS_TITLE, news.getTitle());

			// key value

			Column annotation = item.getAnnotation(Column.class);
			String key="";
			String value="";
			if (annotation != null) {
				key = annotation.value();
				try {
					value = item.get(t).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//判断主键：如果是自增不能设置在values
				
				ID id = item.getAnnotation(ID.class);
				if(id!=null)
				{
					if(id.autoincrement())
					{
						
					}else
					{
						values.put(key, value);
					}
				}else
				{
					values.put(key, value);
				}
			}
			
			
			
			

		}

	}

	@Override
	public int update(T t) {
		// 问题一：将T的实例里面封装的信息传递ContentValues
		ContentValues values = new ContentValues();
		fillContentValues(t, values);
		// 问题二：表名获取
		// 问题三：T对应主键的信息获取到作为更新的条件
		database.update(getTableName(), values, DBHelper.TABLE_ID + "=?", new String[] { getIDValue(t) });
		return 0;
	}

	/**
	 * 获取到实体当中封装的主键信息
	 * 
	 * @param t
	 * @return
	 */
	private String getIDValue(T t) {
		Field[] declaredFields = t.getClass().getDeclaredFields();
		for (Field item : declaredFields) {
			item.setAccessible(true);
			ID annotation = item.getAnnotation(ID.class);
			if (annotation != null) {
				try {
					return item.get(t).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	private String getTableName() {
		T t = getInstance();
		TableName annotation = t.getClass().getAnnotation(TableName.class);
		if (annotation != null) {
			return annotation.value();
		}

		return "";
	}

	/**
	 * 获取到T的实例
	 * 
	 * @return
	 */
	public T getInstance() {
		try {
			return (T)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
