package com.wuzhiaite.javaweb.securingweb;

import com.wuzhiaite.javaweb.base.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 前端交互
 * @author lpfs
 */
@Component
public class SelfAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    private SpringDataUserDetailsService userDetailsService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 进行密码验证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);
        if (!encoder.matches(password, userInfo.getPassword())) {
            double loginNums = (double) redisUtil.hget("loginNums", userName);
            if(!StringUtils.isEmpty(loginNums) && loginNums >= 3){
                throw new RuntimeException("已经错误登录了3次，请1分钟之后再重试！");
            }else{
                loginNums = redisUtil.hincr("loginNums",userName,1);
                if(loginNums == 3){
                    redisUtil.hset("loginNums",userName,loginNums,1*60);
                }else{
                    redisUtil.hset("loginNums",userName,loginNums) ;
                }
            }
            throw new BadCredentialsException("用户名密码不正确，请重新登陆！");
        }else{
            redisUtil.hdel("loginNums" , userName );
        }
        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }



    public static void main(String[] args) throws InterruptedException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123";
        String encode = encoder.encode(password);
        System.out.println(encode);
    }



}