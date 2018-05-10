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
package com.sunitkatkar.blogspot.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sunitkatkar.blogspot.converters.CourseJsonConverter;

/**
 * JPA Entity which will be saved in the database table named 'student'. The
 * attribute (or table column) <tt>course</tt> needs to be converted to a JSON
 * string for storage and converted back to {@link Course} POJO when read.
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * This attribute is the Course POJO which needs to be stored as a JSON
     * string
     */
    @Convert(converter = CourseJsonConverter.class)
    @Column(name = "course")
    private Course course;

    /**
     * Just a field to get the Course POJO as a JSON string and display on the
     * UI
     */
    private String json;

    /**
     * @return the json
     */
    /**
     * Default constructor
     */
    public Student() {
    }

    // Getters and setters
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course
     *            the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Method to get the Course POJO as a JSON string for display on UI
     * 
     * @return
     */
    public String getJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().forType(Course.class);

        String jsonString = "";
        try {
            jsonString = writer.withDefaultPrettyPrinter()
                    .writeValueAsString(this.course);
            // System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * @param json
     *            the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }
}