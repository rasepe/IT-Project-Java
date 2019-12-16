package com.it_academyproject.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.it_academyproject.Domains.VicMyAppUser;
import com.it_academyproject.Exceptions.EmptyFieldException;
import com.it_academyproject.jwt_security.repository.MyAppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyContentUtil
{

    private MyAppUserRepository myAppUserRepository;
    public DummyContentUtil (MyAppUserRepository myAppUserRepository)
    {
        this.myAppUserRepository = myAppUserRepository;
    }
    public List<VicMyAppUser> generateDummyUsers()
    {
        List<VicMyAppUser> users = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            users.add(new VicMyAppUser("vickycampo@gmail.com", passwordEncoder.encode("123456")));


            VicMyAppUser myAppUser;
            for (int i = 0; i < users.size() ; i++)
            {
                myAppUser = myAppUserRepository.findByEmail(users.get(i).getEmail());
                if (myAppUser != null)
                {
                    myAppUser.setPassword(users.get(i).getPassword());
                    myAppUserRepository.save(myAppUser);
                }
                else
                {
                    myAppUserRepository.save(users.get(i));
                }


            }
        }
        catch (EmptyFieldException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public static Collection<GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            public String getAuthority() {
                return "ROLE_USER";
            }
        };
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

}
