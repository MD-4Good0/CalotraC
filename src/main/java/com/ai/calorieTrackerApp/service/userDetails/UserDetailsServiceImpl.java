package com.ai.calorieTrackerApp.service.userDetails;

import com.ai.calorieTrackerApp.repositories.UserModelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserModelRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var user= repo.findByEmailId(username)
                .orElseThrow(()->new UsernameNotFoundException("Not Found"));
        var authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getEmailId(),user.getPassword(),authorities);
    }
}
