package com.glc.managment.common.db.jpa;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.PostgreSQL81IdentityColumnSupport;

/**
 * Created by ssolomon on 9/21/18.
 */
public class GlcPostgreSQLDialect extends PostgreSQL82Dialect {

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new PostgreSQL81IdentityColumnSupport(){

            @Override
            public String getIdentitySelectString(String table, String column, int type) {
                //Remove quotations that may have been added on reserved table names
                return "select currval('" + table.replace("\"", "") + '_' + column + "_seq')";
            }
        };
    }
}