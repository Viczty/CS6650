package dal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Use dal.ConnectionManager to connect to your database instance.
 * 
 * dal.ConnectionManager uses the MySQL Connector/J driver to connect to your local MySQL instance.
 * 
 * In our example, we will create a DAO (data access object) java class to interact with
 * each MySQL table. The DAO java classes will use dal.ConnectionManager to open and close
 * connections.
 * 
 * Instructions:
 * 1. Install MySQL Community Server. During installation, you will need to set up a user,
 * password, and port. Keep track of these values.
 * 2. Download and install Connector/J: http://dev.mysql.com/downloads/connector/j/
 * 3. Add the Connector/J JAR to your buildpath. This allows your application to use the
 * Connector/J library. You can add the JAR using either of the following methods:
 *   A. When creating a new Java project, on the "Java Settings" page, go to the 
 *   "Libraries" tab.
 *   Click on the "Add External JARs" button.
 *   Navigate to the Connector/J JAR. On Windows, this looks something like:
 *   C:\Program Files (x86)\MySQL\Connector.J 8.0\mysql-connector-java-8.0.16-bin.jar
 *   B. If you already have a Java project created, then go to your project properties.
 *   Click on the "Java Build Path" option.
 *   Click on the "Libraries" tab, click on the "Add External Jars" button, and
 *   navigate to the Connector/J JAR.
 * 4. Update the "private final" variables below.
 */
public class ConnectionManager {

	// User to connect to your database instance. By default, this is "root2".
	// private final String user = "root";
	private static final String CLOUD_SQL_CONNECTION_NAME = "my-project-1555284861245:us-west2:skidata";
	private final String DB_USER = "root";
	// Password for the user.
	// private final String password = "zty199261";
	private final String DB_PASS = "zty199261";
	// URI to your database server. If running on the same machine, then this is "localhost".
	private final String DB_NAME = "SkiData";
	// private final String hostName = "localhost";
	// Port to your database server. By defaul database-1.cpyfhaatedas.us-west-2.rds.amazonaws.com, this is 3307.
	// private final int port= 3306;
	// private final String port= System.getenv("MYSQL_PORT");
	// Name of the MySQL schema that contains your tables.
	// Default timezone for MySQL server.
	private final String timezone = "UTC";
	private DataSource pool = null;
	/** Get the connection to the database instance. */
	public Connection getConnection() throws SQLException {
		if (pool == null) {
			HikariConfig config = new HikariConfig();

			// Configure which instance and what database user to connect with.
			config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
			config.setUsername(DB_USER); // e.g. "root", "postgres"
			config.setPassword(DB_PASS); // e.g. "my-password"

			// For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections.
			// See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
			config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
			config.addDataSourceProperty("cloudSqlInstance", CLOUD_SQL_CONNECTION_NAME);
			config.addDataSourceProperty("useSSL", "false");

			// ... Specify additional connection properties here.
			// [START_EXCLUDE]

			// [START cloud_sql_mysql_servlet_limit]
			// maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
			// values for this setting are highly variable on app design, infrastructure, and database.
			config.setMaximumPoolSize(5);
			// minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
			// Additional connections will be established to meet this value unless the pool is full.
			config.setMinimumIdle(5);
			// [END cloud_sql_mysql_servlet_limit]

			// [START cloud_sql_mysql_servlet_timeout]
			// setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
			// Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
			// SQLException.
			config.setConnectionTimeout(10000); // 10 seconds
			// idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
			// sit idle for this many milliseconds are retried if minimumIdle is exceeded.
			config.setIdleTimeout(600000); // 10 minutes
			// [END cloud_sql_mysql_servlet_timeout]

			// [START cloud_sql_mysql_servlet_backoff]
			// Hikari automatically delays between failed connection attempts, eventually reaching a
			// maximum delay of `connectionTimeout / 2` between attempts.
			// [END cloud_sql_mysql_servlet_backoff]

			// [START cloud_sql_mysql_servlet_lifetime]
			// maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
			// live longer than this many milliseconds will be closed and reestablished between uses. This
			// value should be several minutes shorter than the database's timeout value to avoid unexpected
			// terminations.
			config.setMaxLifetime(1800000); // 30 minutes
			// [END cloud_sql_mysql_servlet_lifetime]

			// [END_EXCLUDE]

			// Initialize the connection pool using the configuration object.
			pool = new HikariDataSource(config);
			// [END cloud_sql_mysql_servlet_create]
		}
		return pool.getConnection();
	}

	/** Close the connection to the database instance. */
	public void closeConnection(Connection connection) throws SQLException {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
