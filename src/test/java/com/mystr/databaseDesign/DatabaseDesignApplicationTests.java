package com.mystr.databaseDesign;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class DatabaseDesignApplicationTests {

	@Autowired
	DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		// 查看默认的数据源 ：class com.zaxxer.hikari.HikariDataSource
		System.out.println(dataSource.getClass());
		// 获得数据库连接
		Connection connection = dataSource.getConnection();
		// 查看获得的连接 HikariProxyConnection@354350463 wrapping com.mysql.cj.jdbc.ConnectionImpl@10895b16
		// 是 JDBC 的连接！
		System.out.println(connection);
		connection.close();
	}

}
