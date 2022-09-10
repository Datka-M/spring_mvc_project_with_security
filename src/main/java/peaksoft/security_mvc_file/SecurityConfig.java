package peaksoft.security_mvc_file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/allCompanies").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")
                .antMatchers("/new").hasAuthority("ADMIN")
                .antMatchers("/edit/**").hasAuthority("ADMIN")
                .antMatchers("/delete/**").hasAuthority("ADMIN")
                .antMatchers("/allCourses/**").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")
                .antMatchers("/newCourse/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/editCourse/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/deleteCourse/**/**").hasAuthority("ADMIN")
                .antMatchers("/allInstructors/**").hasAuthority("ADMIN")
                .antMatchers("/newInstructor/**").hasAuthority("ADMIN")
                .antMatchers("/editInstructor/**").hasAuthority("ADMIN")
                .antMatchers("/deleteInstructor/**/**").hasAuthority("ADMIN")
                .antMatchers("/allStudents/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/newStudent/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/editStudent/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/deleteStudent/**/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/allLessons/**").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")
                .antMatchers("/newLesson/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/editLesson/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/deleteLesson/**/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/allTask/**/**").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")
                .antMatchers("/newTask/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/editTask/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/deleteTask/**/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/allVideo/**").hasAnyAuthority("INSTRUCTOR","STUDENT","ADMIN")
                .antMatchers("/newVideo/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/editVideo/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .antMatchers("/deleteVideo/**/**").hasAnyAuthority("ADMIN","INSTRUCTOR")
                .and()
                .formLogin().permitAll()
                .defaultSuccessUrl("/allCompanies")
                .and()
                .logout().permitAll();
    }
}
