package com.it_academyproject.jwt_security.security;

import com.it_academyproject.Domains.MyAppUser;
import com.it_academyproject.Exceptions.EmptyFieldException;
import com.it_academyproject.Exceptions.WrongEmailPassword;
import com.it_academyproject.jwt_security.constants.SecurityConstants;
import com.it_academyproject.repositories.MyAppUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configurable
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private MyAppUserRepository myAppUserRepository;

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager , MyAppUserRepository myAppUserRepository)
    {
        this.authenticationManager = authenticationManager;
        this.myAppUserRepository = myAppUserRepository;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        LoginData loginData = new LoginData();
        try
        {
            String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator())).toString();

            JSONObject  loginDataJson = new JSONObject ( test );
            if ( (loginDataJson.has("email")) &&
                    (loginDataJson.has("password")) &&
                    (! loginDataJson.getString("email").equals("") ) &&
                    (! loginDataJson.getString("password").equals("") )
            )
            {
                loginData.setEmail(loginDataJson.getString("email"));
                loginData.setPassword(loginDataJson.get("password").toString());

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                MyAppUser myAppUser = myAppUserRepository.findByEmail(loginData.getEmail());
                if ( passwordEncoder.matches(loginData.getPassword() , myAppUser.getPassword() ))
                {
                    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword(), grantedAuthorityList );

                    return authenticationManager.authenticate(authenticationToken);
                }
                else
                {
                    //double check on the user and password.
                    throw (new WrongEmailPassword("Wrong Email or Password."));
                }
            }
            else if ( !(loginDataJson.has("email")) || (loginDataJson.getString("email").equals("")))
            {
                throw (new EmptyFieldException("email"));
            }
            else if ( !(loginDataJson.has("password")) || (loginDataJson.getString("password").equals("")))
            {
                throw (new EmptyFieldException("password"));
            }

        }
        catch (WrongEmailPassword e)
        {
            response.setStatus(401);
            try {
                response.getWriter().write(e.getLocalizedMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (EmptyFieldException e)
        {
            response.setStatus(401);
            try {
                response.getWriter().write(e.getLocalizedMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e)
        {
            response.setStatus(401);
        }
        return null;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());

        Object roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(userDetails.getUsername())
                /*.setExpiration(new Date(System.currentTimeMillis() + 864000000))*/
                .setExpiration(new Date(System.currentTimeMillis() + 86400))
                .claim("rol", roles)
                .compact();
        System.out.println("The expiration date of the token is: " + (new Date(System.currentTimeMillis() + 86400).toString()));

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }
}

