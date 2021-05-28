package com.mg.framework.entity.multiTenant;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Component
public class MgMultiTenantConnectionProvider implements MultiTenantConnectionProvider {//,Stoppable, Configurable,ServiceRegistryAwareService {
    private static Logger logger = LoggerFactory.getLogger(MgMultiTenantConnectionProvider.class);

//    private final DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

    private static DataSource ds;

    public DataSource getDataSource() {
        return ds;
    }

    public void setDataSource(DataSource dataSource) {
        ds = dataSource;
    }


    private EntityManager entityManager;
//    ConnectionProvider cp =
//    private final DriverManagerConnectionProviderImpl connectionProvider = ConnectionProviderUtils.buildConnectionProvider("master");
        /**
         * Allows access to the database metadata of the underlying database(s) in situations where we do not have a
         * tenant id (like startup processing, for example).
         *
         * @return The database metadata.
         * @throws SQLException Indicates a problem opening a connection
         */
        @Override
        public Connection getAnyConnection() throws SQLException {
            return DataSourceUtils.getConnection(ds);//ds.getConnection();

//            return entityManager.unwrap(Connection.class);
//            final Connection connection = connectionProvider.getConnection();
//            return connection;
        }

        /**
         * Release a connection obtained from {@link #getAnyConnection}
         *
         * @param connection The JDBC connection to release
         * @throws SQLException Indicates a problem closing the connection
         */
        @Override
        public void releaseAnyConnection(Connection connection) throws SQLException {
//            connection.createStatement().execute("USE devbook");
//            connection.close();
            DataSourceUtils.releaseConnection(connection, ds);
//            entityManager.unwrap(Connection.class).close();
        }

        /**
         * Obtains a connection for Hibernate use according to the underlying strategy of this provider.
         *
         * @param tenantIdentifier The identifier of the tenant for which to get a connection
         * @return The obtained JDBC connection
         * @throws SQLException            Indicates a problem opening a connection
         * @throws org.hibernate.HibernateException Indicates a problem otherwise obtaining a connection.
         */
        @Override
        public Connection getConnection(String tenantIdentifier) throws SQLException {
//            final Connection connection = ds.getConnection();
//            connection.createStatement().execute("USE " + tenantIdentifier);

//            return connection;
//            return null;
            return DataSourceUtils.getConnection(ds);
        }

        /**
         * Release a connection from Hibernate use.
         *
         * @param tenantIdentifier The identifier of the tenant.
         * @param connection       The JDBC connection to release
         * @throws SQLException            Indicates a problem closing the connection
         * @throws org.hibernate.HibernateException Indicates a problem otherwise releasing a connection.
         */
        @Override
        public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
//            connection.createStatement().execute("USE devbook");
//            entityManager.unwrap(Connection.class).close();
            DataSourceUtils.releaseConnection(connection, ds);
        }

        /**
         * Does this connection provider support aggressive release of JDBC
         * connections and re-acquisition of those connections (if need be) later?
         * <p/>
         * This is used in conjunction with {@link org.hibernate.cfg.Environment#RELEASE_CONNECTIONS}
         * to aggressively release JDBC connections.  However, the configured ConnectionProvider
         * must support re-acquisition of the same underlying connection for that semantic to work.
         * <p/>
         * Typically, this is only true in managed environments where a container
         * tracks connections by transaction or thread.
         * <p/>
         * Note that JTA semantic depends on the fact that the underlying connection provider does
         * support aggressive release.
         *
         * @return {@code true} if aggressive releasing is supported; {@code false} otherwise.
         */
        @Override
        public boolean supportsAggressiveRelease() {
            return false;
        }

        /**
         * Can this wrapped service be unwrapped as the indicated type?
         *
         * @param unwrapType The type to check.
         * @return True/false.
         */
        @Override
        public boolean isUnwrappableAs(Class unwrapType) {
            return false;
        }

        /**
         * Unproxy the service proxy
         *
         * @param unwrapType The java type as which to unwrap this instance.
         * @return The unwrapped reference
         */
        @Override
        public <T> T unwrap(Class<T> unwrapType) {
            return null;
        }

}
