package co.com.nequi.r2dbc.config;

import java.time.Duration;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgreSQLConnectionPool {
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;

	@Value("${spring.r2dbc.database}")
	public String database;
	@Value("${spring.r2dbc.host}")
	public String host;
	@Value("${spring.r2dbc.port}")
	public Integer port;
	@Value("${spring.r2dbc.username}")
	public String username;
	@Value("${spring.r2dbc.password}")
	public String password;
	@Value("${spring.r2dbc.schema}")
	public String schema;

	@Bean
	public ConnectionPool getConnectionConfig() {
		PostgresqlConnectionProperties pgProperties = new PostgresqlConnectionProperties();
		pgProperties.setDatabase(database);
		pgProperties.setHost(host);
		pgProperties.setPort(port);
		pgProperties.setUsername(username);
		pgProperties.setPassword(password);
		pgProperties.setSchema(schema);

		return buildConnectionConfiguration(pgProperties);
	}

	private ConnectionPool buildConnectionConfiguration(PostgresqlConnectionProperties properties) {
		PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
				.host(properties.getHost())
				.port(properties.getPort())
				.database(properties.getDatabase())
				.schema(properties.getSchema())
				.username(properties.getUsername())
				.password(properties.getPassword())
				.build();

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
                .name("api-postgres-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

		return new ConnectionPool(poolConfiguration);
	}
}
