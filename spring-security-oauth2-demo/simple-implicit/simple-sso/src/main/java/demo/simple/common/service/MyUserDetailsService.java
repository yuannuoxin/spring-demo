package demo.simple.common.service;

import demo.simple.common.model.MyUser;
import demo.simple.common.model.UrlGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description: 用户信息查询逻辑
 *
 * @auther: csp
 * @date:  2019/1/7 下午9:06
 *
 */
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户的用户名: {}", username);

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();


        // 模拟下逻辑，简单处理下。
        if ("admin".equals(username)) {
            // 自定义权限实现
            UrlGrantedAuthority authority = new UrlGrantedAuthority(null, "/admin/index");
            list.add(authority);
            // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
            User user = new MyUser(username, "123456", list);
            ((MyUser) user).setUserId("111");

            return user;
        }
        else if ("user".equals(username)) {
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
            User user = new MyUser(username, "123456", list);
            ((MyUser) user).setUserId("222");

            return user;
        }
        else {
            throw new DisabledException("用户不存在");
        }

    }
}