package com.security;

import com.security.entities.User;
import com.security.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

	@Test
	void contextLoads() {
        User user = new User(5L,"indu@gamil.com","Indu","indu123");
        String token = jwtServiceImpl.generateToken(user);
        System.out.println(token);

        Long userId = jwtServiceImpl.getUserIdFromToken(token);
        System.out.println(userId);
	}

}
