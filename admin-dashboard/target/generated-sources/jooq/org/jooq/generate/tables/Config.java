/*
 * This file is generated by jOOQ.
*/
package org.jooq.generate.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.generate.Hospitallapp;
import org.jooq.generate.Indexes;
import org.jooq.generate.Keys;
import org.jooq.generate.tables.records.ConfigRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Config extends TableImpl<ConfigRecord> {

    private static final long serialVersionUID = 1056888378;

    /**
     * The reference instance of <code>hospitallapp.config</code>
     */
    public static final Config CONFIG = new Config();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigRecord> getRecordType() {
        return ConfigRecord.class;
    }

    /**
     * The column <code>hospitallapp.config.property_name</code>.
     */
    public final TableField<ConfigRecord, String> PROPERTY_NAME = createField("property_name", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>hospitallapp.config.property_value</code>.
     */
    public final TableField<ConfigRecord, String> PROPERTY_VALUE = createField("property_value", org.jooq.impl.SQLDataType.VARCHAR(400).nullable(false), this, "");

    /**
     * Create a <code>hospitallapp.config</code> table reference
     */
    public Config() {
        this(DSL.name("config"), null);
    }

    /**
     * Create an aliased <code>hospitallapp.config</code> table reference
     */
    public Config(String alias) {
        this(DSL.name(alias), CONFIG);
    }

    /**
     * Create an aliased <code>hospitallapp.config</code> table reference
     */
    public Config(Name alias) {
        this(alias, CONFIG);
    }

    private Config(Name alias, Table<ConfigRecord> aliased) {
        this(alias, aliased, null);
    }

    private Config(Name alias, Table<ConfigRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hospitallapp.HOSPITALLAPP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CONFIG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigRecord>>asList(Keys.KEY_CONFIG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Config as(String alias) {
        return new Config(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Config as(Name alias) {
        return new Config(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Config rename(String name) {
        return new Config(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Config rename(Name name) {
        return new Config(name, null);
    }
}
