package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.services.UserDetService;

@EnableWebSecurity    //  Этой аннотацией Включаем безопасность - в ней есть @Configurations
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {   //  Наследуемся от спрингового конфигуратора безопасности
    private final SuccessUserHandler successUserHandler;  //  объект с настройками - куда переходить для какой роли
    private final UserDetService userDetService;
    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetService userDetService) {
        this.successUserHandler = successUserHandler;
        this.userDetService = userDetService;
    }

    @Override   //  переопределяем метод из конфигуратора настраивая вход и авторизацию
    protected void configure(HttpSecurity http) throws Exception {
        http                                          // учим приложение не пускать пользователей, куда нельзя
                .authorizeRequests()
                .antMatchers("/admins/**").hasRole("ADMIN")
                //  везде в папке админс может ползать только юзер с ролью админ
                .antMatchers("/users/**").hasRole("USER")
                //  везде в папке юзерс может ползать только юзер с ролью админ либо юзер
                .antMatchers("/index").permitAll()
                // на страницу индекс может зайти любой
                .anyRequest().authenticated()
                // на все другие страницы только авторизованный пользователь
                .and()
                //  настройка аутентификации - если вы ещё не залогины, то вас перекинет сюда при любом запросе,
                //  требующем логирования
                .formLogin()
                //стандартная форма логина
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                // стандартный дефолтный логаут по адресу /logout
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {  // кодирует пароль
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {  // Мы вводим логин и пароль,
        // а этот метод проверяет есть ли юзер с такими данными в базе, если есть
        // - положить в спрингСекьюритиКонтекст
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());  // передаём кодировщик,
        // что бы проверяющий мог проверить закодированные пароли
        authenticationProvider.setUserDetailsService(userDetService);
        //  что бы узнать существуют ли юзеры

        return authenticationProvider;
    }

//    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}