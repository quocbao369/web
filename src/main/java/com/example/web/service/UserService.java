package com.example.web.service;

import com.example.web.dto.UserInforDTO;
import com.example.web.model.InforUser;
import com.example.web.model.User;
import com.example.web.repository.UserInforRepository;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInforRepository userInforRepository;

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        String storedPassword = user.getPassword();

        return password.equals(storedPassword);
    }

    public boolean check_username(String username){
        User user = userRepository.findByUsername(username);
        return user == null;
    }
    public boolean check_email(String email){
        User mail = userRepository.findByEmail(email);
        return mail ==null;
    }
    public boolean check_phone(String phone){
        InforUser num = userInforRepository.findByPhone(phone);
        return num ==null;
    }
    public boolean check_pass(String password, String repassword){
        return Objects.equals(password, repassword);
    }
    public boolean registerUser(UserInforDTO registerDTO) {

            User user = new User();
            InforUser infor = new InforUser();

            user.setUserID("MTK"+registerDTO.getUsername());
            user.setUsername(registerDTO.getUsername());
            user.setPassword(registerDTO.getPassword());
            user.setEmail(registerDTO.getEmail());
            user.setRole("user");
            userRepository.save(user);

            infor.setUserID("MTK"+registerDTO.getUsername());
            infor.setFirstName(registerDTO.getFirstName());
            infor.setLastName(registerDTO.getLastName());
            infor.setPhone(registerDTO.getPhone());
            infor.setAddress(registerDTO.getAddress());
            infor.setRegistrationDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            userInforRepository.save(infor);
        return true;
    }
    public String getUserID(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null) ? user.getUserID() : null;
    }
    public String getRole(String username) {
        User user = userRepository.findByUsername(username);

        return (user != null) ? user.getRole() : null;
    }
    public User getUser(String userID){
        return userRepository.findByUserID(userID);
    }
    public InforUser getInfor(String userID){
        return userInforRepository.findByUserID(userID);
    }
    public long countUsers() {
        return userRepository.count();
    }
}
