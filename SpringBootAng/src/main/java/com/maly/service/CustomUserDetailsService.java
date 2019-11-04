package com.maly.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maly.dao.UserDao;
import com.maly.dto.User;
import com.maly.dto.UserPrincipal;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
    PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username).orElseThrow(
	            () -> new UsernameNotFoundException("User not found with name : " + username)
	        );
		UserThreadHolder.userDetails.set(user);
		log.info("user information details in service :{}",UserThreadHolder.userDetails.get());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserDetails userDet = new UserPrincipal();
		BeanUtils.copyProperties(user, userDet);
		log.info("user Details:{}",user);
		log.info("user Details:{}",userDet);
		return userDet;
	}
	
	@Transactional
    public UserDetails loadUserById(Long id) {
		User user = userDao.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );
		
		UserDetails userDet = new UserPrincipal();
		BeanUtils.copyProperties(user, userDet);
		log.info("user Details:{}",user);
		log.info("user Details:{}",userDet);
		return userDet;

    }
	

}
