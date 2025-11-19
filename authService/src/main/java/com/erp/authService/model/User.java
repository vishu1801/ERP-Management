package com.erp.authService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "family")
    @JsonIgnore
    private List<User> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "family_id")
    @JsonIgnore
    private User family;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.groups.stream()
                .map(Group::getModules)
                .flatMap(Collection::stream)
                .map(Module::getActions)
                .flatMap(Collection::stream)
                .map(moduleAction -> new SimpleGrantedAuthority(moduleAction.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
