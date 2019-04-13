package com.glc.managment.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.glc.managment.common.AbstractEntity;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class ModelSerializationFilter extends SimpleBeanPropertyFilter {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ModelSerializationFilter.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {

        if (include(writer)) {

            final String fieldName = writer.getName();

            Class<?> fieldType = ((BeanPropertyWriter) writer).getType().getRawClass();

            if(AbstractEntity.class.isAssignableFrom(fieldType)) {

                //The current field contains a reference to another document - serialize the uuid

                AbstractEntity entity = (AbstractEntity) ((BeanPropertyWriter) writer).get(pojo);

                //Checking for entity.metaClass != null ensures that the lazyloaded entity was found in the db
                if (null != entity && null != entity.getClass() && null != entity.getUuid()) {
                    jgen.writeFieldName(fieldName);
                    jgen.writeString(entity.getUuid());

                } else if(null != entity && null == entity.getClass()) {
                    if(pojo instanceof AbstractEntity) {
                        log.error(String.format("Referenced value not found for %s UUID: %s field: %s",
                                pojo.getClass().getSimpleName(), ((AbstractEntity)pojo).getUuid(), fieldName));
                    }
                    else {
                        log.error(String.format("Referenced value not found for %s field: %s", pojo.getClass().getSimpleName(), fieldName));
                    }

                } else if(null == entity) {
                    jgen.writeFieldName(fieldName);
                    jgen.writeNull();
                }

            } else if(List.class.isAssignableFrom(fieldType)) {

                List<?> list = (List<?>) ((BeanPropertyWriter) writer).get(pojo);

                if (null != list && !list.isEmpty()) {

                    Class<?> listContentType = list.get(0).getClass();

                    if (AbstractEntity.class.isAssignableFrom(listContentType)) {

                        //The current field contains a list of items that refer to another entity/document - serialize the uuids of each item into an array

                        jgen.writeArrayFieldStart(fieldName);

                        for (int i = 0; i < list.size(); i++) {

                            AbstractEntity entity = (AbstractEntity) list.get(i);

                            //Checking for entity.metaClass != null ensures that the lazyloaded entity was found in the db
                            if (null != entity && null != entity.getClass() && null != entity.getUuid()) {
                                jgen.writeString(entity.getUuid());
                            } else if(null != entity && null == entity.getClass()) {
                                if(pojo instanceof AbstractEntity) {
                                    log.error(String.format("Referenced value not found for %s UUID: %s field: %s",
                                            pojo.getClass().getSimpleName(), ((AbstractEntity)pojo).getUuid(), fieldName));
                                }
                                else {
                                    log.error(String.format("Referenced value not found for %s field: %s", pojo.getClass().getSimpleName(), fieldName));
                                }
                            }


                        }

                        jgen.writeEndArray();

                    } else {

                        //List contains non entity type
                        writer.serializeAsField(pojo, jgen, provider);
                    }

                }

            } else if(Date.class.isAssignableFrom(fieldType)) {

                Date date = (Date) ((BeanPropertyWriter) writer).get(pojo);

                if (null != date) {

                    //Save date as timestamp for better lookup and sorting - the default deserializer "knows" how to convert it back to Date
                    jgen.writeFieldName(fieldName);
                    jgen.writeNumber(date.getTime());

                } else {
                    jgen.writeFieldName(fieldName);
                    jgen.writeNull();
                }

            } else {

                writer.serializeAsField(pojo, jgen, provider);
            }


        } else if (!jgen.canOmitFields()) {

            writer.serializeAsOmittedField(pojo, jgen, provider);

        }

    }

    @Override
    protected boolean include(PropertyWriter writer) {
        return true;
    }

    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return true;
    }

}
