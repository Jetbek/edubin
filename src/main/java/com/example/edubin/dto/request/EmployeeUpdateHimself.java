package com.example.edubin.dto.request;

import com.example.edubin.enitity.CommentEntity;
import com.example.edubin.enitity.SocialMediaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateHimself extends Employee{

    private LocalDate birthDay;
    private String telegram;
    private String instagram;
    private String google;
    private String linkedIn;
    private String facebook;
    private String twitter;
}
