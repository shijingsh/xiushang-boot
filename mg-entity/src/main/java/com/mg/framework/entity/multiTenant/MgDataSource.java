package com.mg.framework.entity.multiTenant;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mg.framework.sys.PropertyConfigurer;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mg.framework.log.Constants;
import java.sql.SQLException;


public class MgDataSource extends DruidDataSource {
    private static Logger logger = LoggerFactory.getLogger(MgDataSource.class);
    public static final String DEFAULT_DB = PropertyConfigurer.getConfig("SYS.DEFAULT_DB","mg_xiushang");//默认数据库实例;
    @Override
    public DruidPooledConnection getConnection() throws SQLException {
        DruidPooledConnection conn = super.getConnection();

//        if(SecurityUtils.getSecurityManager())
        String currentSchemaName = conn.getCatalog();//.getSchema();
        //logger.trace("get connection. schema = {}", currentSchemaName);

        String schemaName = getSchemaName();
        if(schemaName == null) {
           // logger.warn("no schema name to use, use the default schema.");
            conn.setCatalog(DEFAULT_DB);
            conn.createStatement().execute("USE "+DEFAULT_DB);
        }
        else if(schemaName.contentEquals(currentSchemaName)){
            //logger.trace("use the same schema connection. nothing to change.");
        }
        else {
            conn.setCatalog(schemaName);
            conn.createStatement().execute("USE " + schemaName);
            //logger.trace("use new schema = {}", schemaName);
        }

        return conn;
    }

    protected String getSchemaName(){
        try {
            String instanceSeqId = getTenantID();
            if(logger.isTraceEnabled()) {
               // logger.trace("get tenant instance id = {}", instanceSeqId);
            }
            if(instanceSeqId == null)
                return null;

            return TenantIdResolver.pre_db + instanceSeqId;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static String getTenantID() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.TENANT_ID);
    }
}
