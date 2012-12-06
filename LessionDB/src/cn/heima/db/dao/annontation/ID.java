package cn.heima.db.dao.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {
	/**
	 * 主键是否是自增长
	 * @return
	 */
	boolean autoincrement();

}
