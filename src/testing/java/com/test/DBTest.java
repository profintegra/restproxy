package com.test;

import org.junit.Test;

import com.db.DataBase;
import com.db.SqlBase;

public class DBTest {
	
	@Test
	public void test(){
		SqlBase.initDB();
		DataBase.selectAll();
	}

}
