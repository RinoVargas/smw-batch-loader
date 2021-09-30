package cl.entel.eai.configuration;

import cl.entel.eai.constants.IMGISError;
import cl.entel.eai.exception.IMGISException;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseConnector {
    protected String url;
    protected String username;
    protected String password;
    protected Connection connection;

        Log logger = LogFactory.getLog(DatabaseConnector.class);

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public void connect() throws IMGISException{

        if (!this.isConnected()) {
            try {
                logger.info("Conectando a la base de datos...");

                OracleDataSource dataSource = new OracleDataSource();
                dataSource.setURL(this.url);
                dataSource.setUser(this.username);
                dataSource.setPassword(this.password);
                dataSource.setImplicitCachingEnabled(true);
                dataSource.setFastConnectionFailoverEnabled(true);
                this.connection = dataSource.getConnection();

                logger.info("Conexión exitosa a la base de datos!");
            } catch (SQLException e) {
                throw new IMGISException(IMGISError.ERROR_DB_NOT_CONNECTED);
            }
        }
    }

    public void close(Statement statement) throws IMGISException {
        try {
            closeConnection();
            if (statement != null) {
                logger.info("Liberando recursos...");
                statement.close();
            }
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws IMGISException {
        try {
            logger.info("Cerrando conexiones con la base de datos...");
            this.connection.close();
            this.connection = null;
            logger.info("Conexiones cerradas.");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}