package com.example.edubin.service;

import com.example.edubin.dto.request.PurchaseRequest;
import com.example.edubin.enitity.CourseEntity;
import com.example.edubin.enitity.FinanceEntity;
import com.example.edubin.enitity.UserEntity;
import com.example.edubin.exception.RecordNotFoundException;
import com.example.edubin.repository.CourseRepository;
import com.example.edubin.repository.FinanceRepository;
import com.example.edubin.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchasingService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final FinanceRepository financeRepository;

    @Transactional(rollbackOn = {UsernameNotFoundException.class, RecordNotFoundException.class})
    public int purchase(int id, PurchaseRequest purchase) {
        CourseEntity course = courseRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(
                MessageFormat.format("course = {0} was not found in database",id)
        ));
        UserEntity user = userRepository.findByEmail(purchase.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                MessageFormat.format("email = {0} was not found in database", purchase.getEmail())
        ));
        FinanceEntity financeEntity =new FinanceEntity();
        financeEntity.setCard(purchase.getCard());
        financeEntity.setExpiredDate(purchase.getExpiredDate());
        financeEntity.setUser(user);
        financeEntity.setCourse(course);
        financeEntity.setPrice(course.getPrice());
        financeEntity.setTransactionDate(LocalDate.now());
        financeEntity.setTransactionTime(LocalTime.now());
        financeRepository.save(financeEntity);
        List<UserEntity> clients = course.getTeacher();
        clients.add(user);
        course.setTeacher(clients);
        courseRepository.save(course);
        return user.getId();
    }
}
