package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final int VERSION = 1;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context, String name) {
		this(context, name,VERSION);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context, String name,int version) {
		this(context, name,null,VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}
	
	@Override
	//函数在第一次创建数据库的时候执行，实际上是第一次得到SQLiteDatabase对象时执行
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("Create a database--------------");
		db.execSQL("create table user(id varchar(20),password varchar(30))");
		db.execSQL("create table course(" +
				"id varchar(20) PRIMARY KEY," +
				"name varchar(20)," +
				"date int," +
				"start int," +
				"duration int," +
				"addr varchar(40)," +
				"teacher varchar(10))");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("Update database----------------");
		
	}

}
