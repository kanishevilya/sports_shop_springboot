package com.example.SpringBootProject.repository;
import com.example.SpringBootProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.banStartTime FROM User u WHERE u.username=:name")
    LocalDateTime getBanStartTimeByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.banStartTime= :time WHERE u.username= :name")
    void setBanStartTimeByName(@Param("name") String name, @Param("time") LocalDateTime localDateTime);

    @Query(value = "SELECT u.numberOfAttempts FROM User u WHERE u.username=:name")
    Integer getAmountOfAttemptsByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.numberOfAttempts= :amount WHERE u.username= :name")
    void setAmountOfAttemptsByName(@Param("name") String name, @Param("amount") Integer amount);


    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    Integer updateEmail(Long id, String email);

    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
    Integer updateUsername(Long id, String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    Integer updatePassword(Long id, String password);

    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName WHERE u.id = :id")
    Integer updateFirstName(Long id, String firstName);

    @Modifying
    @Query("UPDATE User u SET u.lastName = :lastName WHERE u.id = :id")
    Integer updateLastName(Long id, String lastName);

    @Modifying
    @Query("UPDATE User u SET u.city = :city WHERE u.id = :id")
    Integer updateCity(Long id, String city);

    @Modifying
    @Query("UPDATE User u SET u.country = :country WHERE u.id = :id")
    Integer updateCountry(Long id, String country);

    @Modifying
    @Query("UPDATE User u SET u.phone = :phone WHERE u.id = :id")
    Integer updatePhone(Long id, String phone);
}
