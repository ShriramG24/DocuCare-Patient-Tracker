package com.project.PatientTracker.model;

import java.util.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@MappedSuperclass
@Accessors(chain = true)
public class User implements UserDetails{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(name="firstName", nullable=false)
    @Getter @Setter
    private String firstName;

    @Column(name="lastName", nullable=false)
    @Getter @Setter
    private String lastName;

    @Column(name="gender", nullable=false)
    @Getter @Setter
    private String gender;

    @Column(name="age", nullable=false)
    @Getter @Setter
    private int age;

    @Column(name="email", nullable=false, unique=true)
    @Getter @Setter
    private String email;

    @Column(name="phone", nullable=false, unique=true)
    @Getter @Setter
    private String phone;

    @Column(name="password", nullable=false)
    @Getter @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(role.name())); 
    }
  
    @Override
    public String getPassword() {
      return password;
    }
  
    @Override
    public String getUsername() {
      return email;
    }
  
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }
  
    @Override
    public boolean isAccountNonLocked() {
      return true;
    }
  
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }
  
    @Override
    public boolean isEnabled() {
      return true;
    }

   
}
