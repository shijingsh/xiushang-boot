package com.xiushang.framework.entity.model;

import com.xiushang.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TableStringGenerator extends TableGenerator {
    private Map<String, TableGenerator> multiTenantTableGenerator = new HashMap<>();
    private Type defaultType;
    private Properties defaultParams;
    private Dialect defaultDialet;

    public void configure(Type type, Properties params, Dialect dialect)
            throws MappingException {
        this.defaultParams = params;
        this.defaultDialet = dialect;
    }

    public synchronized Serializable generate(SessionImplementor session, Object obj) {
//        Serializable generated = super.generate(session, obj);
//        if(generated instanceof Number) {
//            if(format == null) {
//                return String.valueOf(generated);
//            }
//            return String.format(format, generated);
//        }
//        return generated;

        //
        // 不知道什么原因，JPA或Hibernate无法自定义主键ID。即如果set了一个数据库内不存在
        // 的主键，这条记录虽然会插入，但主键会被改为自动生成的模式。（当然，如果set了一个
        // 数据库中已经存在的ID，则JPA会采用update指令）。
        //
        // 为了解决这个问题，先判断传入的对象的ID是不是为空，如果不是为空则直接用它。以后
        // 还需要考虑长度限制的问题
        //
        if (obj instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) obj;
            if (StringUtils.isNotBlank(baseEntity.getId())) {
                String pk = baseEntity.getId();
                //logger.debug("get pk from BaseEntity: " + pk);
                return pk;
            }
        }
//        // ItemEntity是个特例，目前没有从HRMSBaseEntity中继承
//        else if (obj instanceof ItemEntity) {
//            ItemEntity itemEntity = (ItemEntity) obj;
//            if (StringUtils.isNotBlank(itemEntity.getId())) {
//                String pk = itemEntity.getId();
//                logger.debug("get pk from ItemEntity: " + pk);
//                return pk;
//            }
//        }

        // 是空的，就用传统的方式自动生成。
//        logger.debug("ojb: " + obj.toString());
        String instanceSeqId =  "MgDataSource.getTenantID()";
        String id;
        if(instanceSeqId == null) {
            //logger.warn("没有找到对应的实例，采用缺省的的主键生成方式");
            id = "" + super.generate(session, obj);
        }
        else {
            TableGenerator tableGenerator = multiTenantTableGenerator.get(instanceSeqId);
            if(tableGenerator == null) {
                tableGenerator = new TableGenerator();
                //tableGenerator.configure(new IntegerType(), defaultParams, defaultDialet);
                multiTenantTableGenerator.put(instanceSeqId, tableGenerator);
                //logger.info("创建{}的主键生成器。", instanceSeqId);
            }
            id = "" + tableGenerator.generate(session, obj);
        }
        //logger.debug("generate pk id: {} {}", obj.getClass(), id);
        return id;
    }

}
