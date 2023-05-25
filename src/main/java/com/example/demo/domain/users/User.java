package com.example.demo.domain.users;

import com.example.demo.domain.common.Image;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id
    Long id;

    @Column(unique = true, name = "username")
    String username;

    @Column(name = "name")
    Name name;

    @Column(unique = true, name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    UserRole userRole;

//    @Column(name = "image")
    @OneToOne
    Image image;

    @Column(name = "active")
    Boolean active;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.toString()));

        return authorities;
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
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
