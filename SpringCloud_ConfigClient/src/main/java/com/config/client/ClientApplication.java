package com.config.client;

import com.config.client.entity.User;
import com.config.client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@EnableDiscoveryClient//发现服务
@SpringBootApplication
@RestController
@RefreshScope  // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class ClientApplication {
    @Value("${spring.datasource.type}")  //git配置文件里的key
    String spring;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @GetMapping("/getconfig")
    public String test(){
        System.out.println("spring.datasource.type ="+spring);
        return spring;
    }

    @GetMapping("/add")
    public User add(String username){
        Random random = new Random();
        User user = new User();
        user.setName(username);
        user.setAge(random.nextInt(40)+10);
        return userRepository.save(user);
    }

}
