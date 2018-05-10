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
package com.sunitkatkar.blogspot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunitkatkar.blogspot.model.Student;
import com.sunitkatkar.blogspot.service.StudentService;

/**
 * The home page controller
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Controller
public class IndexController {

    @Autowired
    StudentService studentService;

    /**
     * Show the home page
     * 
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        return "index";
    }
    
    /**
     * Handle the employee creation form submission and return to home page
     * 
     * @param employee
     * @return
     */
    @PostMapping("/create")
    public String create(@ModelAttribute("student") Student student, BindingResult result, Model model) {
        studentService.save(student);
        return "redirect:/";
    }
    
}
