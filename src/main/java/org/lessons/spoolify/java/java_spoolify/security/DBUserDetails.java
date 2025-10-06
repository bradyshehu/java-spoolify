package org.lessons.spoolify.java.java_spoolify.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.lessons.spoolify.java.java_spoolify.models.Role;
import org.lessons.spoolify.java.java_spoolify.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DBUserDetails implements UserDetails{
    
    private Integer id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;


    public DBUserDetails (User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = new HashSet<GrantedAuthority>();

        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    public Integer getId(){
        return this.id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.authorities;
    }
    @Override
    public String getPassword(){
        return this.password;
    }
    @Override
    public String getUsername(){
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
