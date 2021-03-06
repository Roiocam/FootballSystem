# Spring boot provided three way to Configure a Custom DataSource:
To configure your own DataSource, define a @Bean of that type in your configuration. Spring Boot reuses your DataSource anywhere one is required, including database initialization. If you need to externalize some settings, you can bind your DataSource to the environment (see “Section 24.7.1, “Third-party Configuration””).

The following example shows how to define a data source in a bean:
```
@Bean
@ConfigurationProperties(prefix="app.datasource")
public DataSource dataSource() {
	return new FancyDataSource();
}
```
The following example shows how to define a data source by setting properties:
```
app.datasource.url=jdbc:h2:mem:mydb
app.datasource.username=sa
app.datasource.pool-size=30
Assuming that your FancyDataSource has regular JavaBean properties for the URL, the username, and the pool size, these settings are bound automatically before the DataSource is made available to other components. The regular database initialization also happens (so the relevant sub-set of spring.datasource.* can still be used with your custom configuration).
```
You can apply the same principle if you configure a custom JNDI DataSource, as shown in the following example:
```
@Bean(destroyMethod="")
@ConfigurationProperties(prefix="app.datasource")
public DataSource dataSource() throws Exception {
	JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	return dataSourceLookup.getDataSource("java:comp/env/jdbc/YourDS");
}
```
Spring Boot also provides a utility builder class, called DataSourceBuilder, that can be used to create one of the standard data sources (if it is on the classpath). The builder can detect the one to use based on what’s available on the classpath. It also auto-detects the driver based on the JDBC URL.

The following example shows how to create a data source by using a DataSourceBuilder:
```
@Bean
@ConfigurationProperties("app.datasource")
public DataSource dataSource() {
	return DataSourceBuilder.create().build();
}
```
To run an app with that DataSource, all you need is the connection information. Pool-specific settings can also be provided. Check the implementation that is going to be used at runtime for more details.

The following example shows how to define a JDBC data source by setting properties:
```
app.datasource.url=jdbc:mysql://localhost/test
app.datasource.username=dbuser
app.datasource.password=dbpass
app.datasource.pool-size=30
```
However, there is a catch. Because the actual type of the connection pool is not exposed, no keys are generated in the metadata for your custom DataSource and no completion is available in your IDE (because the DataSource interface exposes no properties). Also, if you happen to have Hikari on the classpath, this basic setup does not work, because Hikari has no url property (but does have a jdbcUrl property). In that case, you must rewrite your configuration as follows:
```
app.datasource.jdbc-url=jdbc:mysql://localhost/test
app.datasource.username=dbuser
app.datasource.password=dbpass
app.datasource.maximum-pool-size=30
```
You can fix that by forcing the connection pool to use and return a dedicated implementation rather than DataSource. You cannot change the implementation at runtime, but the list of options will be explicit.

The following example shows how create a HikariDataSource with DataSourceBuilder:
```
@Bean
@ConfigurationProperties("app.datasource")
public HikariDataSource dataSource() {
	return DataSourceBuilder.create().type(HikariDataSource.class).build();
}
```
You can even go further by leveraging what DataSourceProperties does for you — that is, by providing a default embedded database with a sensible username and password if no URL is provided. You can easily initialize a DataSourceBuilder from the state of any DataSourceProperties object, so you could also inject the DataSource that Spring Boot creates automatically. However, that would split your configuration into two namespaces: url, username, password, type, and driver on spring.datasource and the rest on your custom namespace (app.datasource). To avoid that, you can redefine a custom DataSourceProperties on your custom namespace, as shown in the following example:
```
@Bean
@Primary
@ConfigurationProperties("app.datasource")
public DataSourceProperties dataSourceProperties() {
	return new DataSourceProperties();
}
```
```
@Bean
@ConfigurationProperties("app.datasource")
public HikariDataSource dataSource(DataSourceProperties properties) {
	return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
			.build();
}
```
This setup puts you in sync with what Spring Boot does for you by default, except that a dedicated connection pool is chosen (in code) and its settings are exposed in the same namespace. Because DataSourceProperties is taking care of the url/jdbcUrl translation for you, you can configure it as follows:
```
app.datasource.url=jdbc:mysql://localhost/test
app.datasource.username=dbuser
app.datasource.password=dbpass
app.datasource.maximum-pool-size=30
```
[Note]
Because your custom configuration chooses to go with Hikari, app.datasource.type has no effect. In practice, the builder is initialized with whatever value you might set there and then overridden by the call to .type().

See “Section 29.1, “Configure a DataSource”” in the “Spring Boot features” section and the DataSourceAutoConfiguration class for more details.