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

import javax.persistence.Converter;

import com.sunitkatkar.blogspot.model.Course;

/**
 * Concrete converter class which extends the {@link GenericJsonAttributeConverter}
 * class. <br/>
 * Note: No need to do anything here. This is used to pass the attribute type to
 * the JsonAttributeConverter
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Converter(autoApply = false)
public class CourseJsonConverter extends GenericJsonAttributeConverter<Course> {

}
