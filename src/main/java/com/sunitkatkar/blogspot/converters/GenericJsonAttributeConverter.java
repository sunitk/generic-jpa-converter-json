/**
 * Copyright 2018 onwards - Sunit Katkar - 
 * URL: https://sunitkatkar.blogspot.com  
 * Email: sunitkatkar@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunitkatkar.blogspot.converters;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * JPA converter class which converts any POJO like object to a JSON string and
 * stores it in the database table column. The JSON string contains the class
 * name of the POJO and its JSON format string. <br/>
 * 
 * Note: An abstract class is not needed as this class takes care of all the
 * JSON serialization and deserialization. You only need to extend this class so
 * that you can use your POJO class for conversion. The extended class should be
 * annotated with the {@literal @}Converter annotation
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 * @param <X>
 */
public class GenericJsonAttributeConverter<X>
        implements AttributeConverter<X, String> {

    protected static final Logger LOG = LoggerFactory
            .getLogger(GenericJsonAttributeConverter.class);

    /**
     * To 'write' the attribute POJO as a JSON string
     */
    private final ObjectWriter writer;

    /**
     * To 'read' the JSON string and convert to the attribute POJO
     */
    private final ObjectReader reader;

    /**
     * Default constructor
     */
    public GenericJsonAttributeConverter() {

        ObjectMapper mapper = new ObjectMapper();

        // We want all keys in the key value pair JSON even if the key has no
        // value
        mapper.setSerializationInclusion(Include.ALWAYS);

        //@formatter:off
        // The mapper should be able to 'see' the entity attributes (fields)
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        //@@formatter:on

        // We are wrapping the entity attribute in a class. See class for
        // details.
        reader = mapper.reader().forType(JsonTypeLike.class);
        writer = mapper.writer().forType(JsonTypeLike.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.
     * Object)
     */
    @Override
    public String convertToDatabaseColumn(X attribute) {
        try {
            if (attribute != null) {
                JsonTypeLike<X> wrapper = new JsonTypeLike<X>(attribute,
                        writer);
                String value = writer.writeValueAsString(wrapper);
                return value;
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            LOG.error("Failed to serialize as object into JSON: {}", attribute,
                    e);
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.
     * Object)
     */
    @Override
    public X convertToEntityAttribute(String dbData) {
        try {
            if (dbData != null) {
                JsonTypeLike<X> wrapper = reader.readValue(dbData);
                X obj = wrapper.readValue(reader);
                return obj;
            } else {
                return null;
            }
        } catch (IOException e) {
            LOG.error("Failed to deserialize as object from JSON: {}", dbData,
                    e);
            throw new RuntimeException(e);
        }
    }

    /**
     * The concrete type is needed for Jackson to serialize or deserialize. This
     * class is created to wrap the entity type &lt;Y&gt; so that the Jackson
     * {@link ObjectReader#forType(Class)} and
     * {@link ObjectWriter#forType(Class))} can be used to get the concrete type
     * of the attribute being serialized/deserialized.
     * 
     * @author Sunit Katkar, sunitkatkar@gmail.com
     *
     * @param <Y>
     */
    public static class JsonTypeLike<Y> {

        // For adding the type (class) of the entity in the generated JSON
        private String entityType;

        // For the actual value which is obtained by reading or writing the JSON
        private String entityValue;

        public JsonTypeLike() {
        }

        /**
         * Constructor which helps initialize the ObjectWriter by providing the
         * concrete class type to the writer
         * 
         * @param obj
         * @param writer
         */
        public JsonTypeLike(Y obj, ObjectWriter writer) {
            // TODO: Need a better way to do type conversion and not get safety
            // warning
            Class<Y> classType = ((Class<Y>) obj.getClass());
            // We are saving the class type as a string in entityType so that
            // while reading
            // back the JSON, the target Class type is known o the mapper.
            this.entityType = obj.getClass().getName();

            try {
                this.entityValue = writer.forType(classType)
                        .writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                LOG.error("Failed serializing object to JSON: {}", obj, e);
            }
        }

        /**
         * Read the JSON format string and create the target Java POJO object
         * 
         * @param reader
         * @return
         */
        public Y readValue(ObjectReader reader) {
            try {
                // Once the json string is read, we need to know what is the
                // destination class
                // type. So reading the entityType value helps in getting the
                // Class details.
                Class<?> clazz = Class.forName(this.entityType);
                Y obj = reader.forType(clazz).readValue(this.entityValue);
                return obj;
            } catch (ClassNotFoundException | IOException e) {
                LOG.error("Failed deserializing object from JSON: {}",
                        this.entityValue, e);
                return null;
            }
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String type) {
            this.entityType = type;
        }

        public String getEntityValue() {
            return entityValue;
        }

        public void setValue(String value) {
            this.entityValue = value;
        }
    }
}