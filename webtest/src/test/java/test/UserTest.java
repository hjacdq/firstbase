package test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hj.entity.User;
import com.hj.service.UserService;

public class UserTest {

	public static void main(String[] args) {
		BeanFactory factory=new ClassPathXmlApplicationContext("spring-mybatis.xml");
        UserService userService=(UserService) factory.getBean("userService");
        User user = new User();
        user.setUsername("test");
        userService.addUser(user);
//        System.out.println(userService);
	}
	
}
