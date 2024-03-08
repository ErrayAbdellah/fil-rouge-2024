package com.mobi.mobe.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mobi.mobe.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor@NoArgsConstructor
@ToString@Getter@Setter@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastNAme;
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Column(name = "bio", nullable = false)
    private String bio;
//    @NotNull
    @Column(name = "profile_picture_url", nullable = false)
    private String profilePictureUrl;
    @Column(name = "gender",nullable = false)
    private Gender gender;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-post")
    private Set<Post> posts;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonManagedReference("user-comment")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonManagedReference("user-post_report")
    private Set<PostReport> postReports;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @ManyToOne(cascade =  {CascadeType.MERGE, CascadeType.DETACH},fetch = FetchType.EAGER)
    private Role role ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
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
