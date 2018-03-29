package com.aotain.common.utils.hadoop;

import com.aotain.common.config.LocalConfig;
import com.aotain.common.config.model.ConnectionInformation;
import com.aotain.common.config.model.IdcHouses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class HadoopUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HadoopUtil.class);
	
	public static Connection getConnection(String houseIdStr) {
		return getConnection(houseIdStr, false);
	}

	public static Connection getConnection(String houseIdStr, boolean hiveConnection) {
		String driver = "", url = "", username = "", password = "";
		Connection conn = null;
		try {
			IdcHouses idcHouse = LocalConfig.getInstance().getIdcHouse(houseIdStr);
			int clusterId = idcHouse.getClusterId();
			ConnectionInformation info = LocalConfig.getInstance().getConnectionInformation(clusterId + "");
			
			if (hiveConnection) {
				driver = info.getHiveDriver();
				url = info.getHiveUrl();
				username = info.getUserName();
				password = info.getPassword();
			} else {
				driver = info.getImpalaDriver();
				url = info.getImpalaUrl();
				username = info.getUserName();
				password = info.getPassword();
			}
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			logger.error("The driver class is not found!", e);
		} catch (SQLException e) {
			logger.error("The SQLException:", e);
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Close connection failed!", e);
			}
		}
	}

	public static void closeConnection(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.error("Close connection failed!", e);
		}
	}

	public static void closeConnection(Connection conn, Statement st) {
		try {
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.error("Close connection failed!", e);
		}
	}

	public static void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			logger.error("Close connection failed!", e);
		}
	}
}
