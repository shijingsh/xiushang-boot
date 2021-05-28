package com.mg.common.context;

import com.mg.common.entity.InstanceEntity;
import com.mg.framework.entity.multiTenant.MgDataSource;
import com.mg.framework.sys.MgApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 在web启动时加载实例映射关系的缓存
 */
public final class InstanceLoaderListener implements ServletContextListener{
    private static Logger logger = LoggerFactory.getLogger(InstanceLoaderListener.class);

    public InstanceLoaderListener(){
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (MgApplicationContext.getApplicationContext()==null)return;
        DataSource dataSource = (DataSource) MgApplicationContext.getApplicationContext().getBean("dataSource");
        //从本机的HRMS_system中获取instance映射缓存
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            PreparedStatement pstmt = connection.prepareStatement("select id, token from "+ MgDataSource.DEFAULT_DB+".sys_instance");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                InstanceEntity instanceEntity = new InstanceEntity();
                String token = rs.getString("token");
                String instanceSeqId = rs.getString("id");
                instanceEntity.setId(instanceSeqId);
                instanceEntity.setToken(token);
                MgServerContext.getInstanceMap().put(instanceSeqId, instanceEntity);
                logger.info("put instance map cache: {} - {}", token, instanceSeqId);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MgServerContext.getInstanceMap().clear();
    }
}
