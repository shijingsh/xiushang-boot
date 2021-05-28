package com.mg.framework.entity.multiTenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TenantIdResolver implements CurrentTenantIdentifierResolver {
    private static Logger logger = LoggerFactory.getLogger(TenantIdResolver.class);

    public static String pre_db = "mg_inst_";
    /**
     * Resolve the current tenant identifier.
     *
     * @return The current tenant identifier
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            String instanceSeqId = MgDataSource.getTenantID();
            if(logger.isTraceEnabled()) {
                logger.trace("get tenant instance id = {}", instanceSeqId);
            }
            if(instanceSeqId == null)
                return MgDataSource.DEFAULT_DB;

            return pre_db + instanceSeqId;
        } catch (Exception e) {
            //logger.error(e.getMessage());
            return MgDataSource.DEFAULT_DB;
        }
    }

    /**
     * Should we validate that the tenant identifier on "current sessions" that already exist when
     * {@link #resolveCurrentTenantIdentifier()}?
     *
     * @return {@code true} indicates that the extra validation will be performed; {@code false} indicates it will not.
     * @see org.hibernate.context.TenantIdentifierMismatchException
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
