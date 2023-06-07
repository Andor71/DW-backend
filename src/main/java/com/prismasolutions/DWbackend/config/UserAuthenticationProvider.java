package com.prismasolutions.DWbackend.config;

import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.enums.UserStatus;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Utility utility;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        if(email == null || password == null)
            throw new BadCredentialsException("Email or password is null");

        Optional<UserEntity> user = userRepository.findByEmail(email);

        if(user.isEmpty())
            throw new BadCredentialsException("Nem található felhasználó a megadott E-mail-el!");

        if(!user.get().getActive() && user.get().getStatus().equals(UserStatus.SEARCHING))
            throw new BadCredentialsException("Nincs aktiválva a felhasználód, nézd meg az E-mail fiokod!");


        if(!user.get().getActive() && user.get().getStatus().equals(UserStatus.FINISHED))
            throw new BadCredentialsException("Deaktiválva lett az felhasználód a diploma leadása után!");

        if(!user.get().getActive())
            throw new BadCredentialsException("Nincs aktiválva a felhasználód, nézd meg az E-mail fiokod!");

        if(user.isEmpty() || !bCryptPasswordEncoder.matches(password, user.get().getPassword()))
            throw new BadCredentialsException("Helytelen bejelntkezési adatok!");


        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole()));

        return new UsernamePasswordAuthenticationToken(user.get().getId(), null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
