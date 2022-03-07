package com.emilie.lib10.Services;

import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Repositories.UserRepository;
import com.emilie.Lib10.Services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@EnableJpaRepositories(basePackages = "com.emilie.Lib10.Repositories")
//@EntityScan("com.emilie.Lib10.Models.User")
//@AutoConfigureDataJpa
//@SpringBootTest(classes = {UserRepository.class, BCryptPasswordEncoder.class})
//@ExtendWith(SpringExtension.class)
/*@Sql(scripts = {"classpath:/com/emilie/lib10/01_create_schema.sql",
        "classpath:/com/emilie/lib10/02_create_tables.sql",
        "classpath:/com/emilie/lib10/21_insert_data_demo.sql" })*/
public class UserServiceImplITest {

    //todo ok on peut faire les testIntegration
    //todo par contre pas sûr que l'on utilise bddDocker pour l'instant

    @Autowired
    private UserServiceImpl userServiceUnderTest;

   /* public UserServiceImplITest(UserServiceImpl userServiceUnderTest) {
        this.userServiceUnderTest = userServiceUnderTest;
    }*/

    //  static UserService userServiceUnderTest;

    @Test
    void getListCompteComptable() {
   /*     // GIVEN
        List<UserDto> userList;

        // WHEN
        userList=userServiceUnderTest.findAll();

        // THEN
        System.out.println(userList.get(0));
        assertThat( userList ).isNotEmpty();*/
    }

}
