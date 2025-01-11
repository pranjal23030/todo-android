package com.example.namiminiproject.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.namiminiproject.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("UPDATE users SET name = :name, email = :email, password = :password WHERE id = :id")
    void updateUser(int id, String name, String email, String password);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email OR name = :name")
    int isUserExist(String email, String name);

    @Query("SELECT COUNT(*) FROM users WHERE name = :name")
    int isUserNameExist(String name);

    @Query("SELECT COUNT(*) FROM users WHERE name = :name AND password = :password")
    int isUserPasswordExist(String name, String password);

}
