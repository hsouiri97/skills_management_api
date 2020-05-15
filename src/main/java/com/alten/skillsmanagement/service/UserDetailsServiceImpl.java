package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.findUserByUsername(username);

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        appUser.getRoles().forEach(appRole -> grantedAuthorities.addAll(appRole.getRoleName().simpleGrantedAuthorities()));

        return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
    }
}
