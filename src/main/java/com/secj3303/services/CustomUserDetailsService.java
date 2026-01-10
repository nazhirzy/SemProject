package com.secj3303.services;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final PersonDaoHibernate pDao;

    public CustomUserDetailsService(PersonDaoHibernate pDao){
        this.pDao = pDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
                Person user = pDao.findByUsername(username);
                if(user == null) throw new UsernameNotFoundException("User not found");

                return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
        true,true, true, true,
                List.of(new SimpleGrantedAuthority(user.getRole()))
            );
            }
}
