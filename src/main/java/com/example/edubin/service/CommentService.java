package com.example.edubin.service;

import com.example.edubin.dto.request.CommentRequest;
import com.example.edubin.enitity.BlogEntity;
import com.example.edubin.enitity.CommentEntity;
import com.example.edubin.enitity.CourseEntity;
import com.example.edubin.enitity.UserEntity;
import com.example.edubin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final BlogService blogService;

    public void addCommentToTeacher(int id, CommentRequest commentRequest) {
        UserEntity teacher = userService.findUser(id);
        CommentEntity comment=new CommentEntity();
        comment.setTeacher(teacher);
        saveAnyTypeComment(commentRequest,comment);
    }

    public void addCommentToCourse(int id, CommentRequest commentRequest) {
        CourseEntity course = courseService.findCourse(id);
        CommentEntity comment=new CommentEntity();
        comment.setCourse(course);
        saveAnyTypeComment(commentRequest,comment);
    }

    public void addCommentToBlog(int id, CommentRequest commentRequest) {
        BlogEntity blog = blogService.findBlogById(id);
        CommentEntity comment=new CommentEntity();
        comment.setBlog(blog);
        saveAnyTypeComment(commentRequest,comment);
    }
    private void saveAnyTypeComment(CommentRequest commentRequest,CommentEntity comment){
        comment.setDate(commentRequest.getDate());
        comment.setText(commentRequest.getText());
        comment.setPersonName(commentRequest.getFirstName()+" "+commentRequest.getLastName());
        commentRepository.save(comment);
    }
}
