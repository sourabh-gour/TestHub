package com.example.testApplication.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.testApplication.entities.PasswordResetToken;
import com.example.testApplication.entities.User;
import com.example.testApplication.repositories.PasswordResetTokenRepo;
import com.example.testApplication.repositories.UserRepo;
import com.example.testApplication.services.UserService;

/**
 * Implementation of the UserService interface.
 * Provides methods for user registration, authentication, and management.
 */
@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private PasswordResetTokenRepo passwordResetTokenRepo;

    /**
     * Constructs a UserServiceImpl with the specified repositories and password encoder.
     *
     * @param userRepo the user repository
     * @param passwordEncoder the password encoder
     * @param passwordResetTokenRepo the password reset token repository
     */
    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, PasswordResetTokenRepo passwordResetTokenRepo){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepo = passwordResetTokenRepo;
    }

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return the registered user
     * @throws RuntimeException if the user already exists
     */
    @Override
    public User registerUser(User user) {
        if(userRepo.existsByEmail(user.getEmail())){
            throw new RuntimeException("User already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return the updated user
     * @throws RuntimeException if the user is not found
     */
    @Override
    public User updateUser(User user) {
        Optional<User> userOptional = userRepo.findById(user.getUserId());
        if(userOptional.isPresent()){
            return userRepo.save(user);
        }
        else{
            throw new RuntimeException("User not found.");
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Changes the password for a user.
     *
     * @param userName the username of the user
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true if the password was changed successfully
     * @throws RuntimeException if the old password is incorrect or the user is not found
     */
    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(!passwordEncoder.matches(oldPassword, user.getPassword())){
                throw new RuntimeException("Old password is incorrect.");
            }

            if (passwordEncoder.matches(oldPassword, newPassword)) {
                throw new RuntimeException("New password cannot be the same as the old password.");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            return true;
        }       
        throw new RuntimeException("User not found.");
    }

    /**
     * Generates a password reset token for a user.
     *
     * @param user the user for whom to generate the token
     * @return the generated token
     */
    @Override
    public String generateResetToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpirationTime(LocalDateTime.now().plusMinutes(60));
        passwordResetTokenRepo.save(passwordResetToken);
        return token;
    }

    /**
     * Loads a user by username.
     *
     * @param username the username of the user
     * @return the UserDetails of the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassword(),
            authorities
        );
    }

    /**
     * Resets the password for a user using a token.
     *
     * @param token the reset token
     * @param newPassword the new password
     * @return true if the password was reset successfully
     * @throws RuntimeException if the token is invalid or expired
     */
    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepo.findByToken(token);
        if(passwordResetTokenOptional.isPresent()){
            PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
            if(passwordResetToken.getExpirationTime().isBefore(LocalDateTime.now())){
                passwordResetTokenRepo.delete(passwordResetToken);
                throw new RuntimeException("Link has expired.");
            } 

            User user = passwordResetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            passwordResetTokenRepo.delete(passwordResetToken);

            return true;
        }
        throw new RuntimeException("Link token not found.");
    }

    /**
     * Finds a user by email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }    
}
