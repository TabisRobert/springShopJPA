package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.entity.ShopUser;
import com.motorola.springShopJPA.repository.ShopUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ShopUserServiceTest {

    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final PasswordEncoder bCryptPasswordEncoder = mock(PasswordEncoder.class);
    private final ShopUserRepository shopUserRepository = mock(ShopUserRepository.class);
    private final UserDetails userDetails = mock(UserDetails.class);
    private final Authentication authentication = mock(Authentication.class);
    private final SecurityContext securityContext = mock(SecurityContext.class);
    private final ShopUserService shopUserService = new ShopUserService(modelMapper, bCryptPasswordEncoder, shopUserRepository);

    private static final ShopUser shopUser = new ShopUser(1L,"asd@asd.asd","asdasd", "ROLE_ADMIN",null);

    @Test
    public void shouldReturnCurrentlyLoggedUser(){
        //given
        String username = "asd@asd.asd";
        SecurityContextHolder.setContext(securityContext);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn(username);
        given(shopUserRepository.findByEmail(username)).willReturn(shopUser);
        //when
        when(shopUserService.findCurrentlyLoggedUser()).thenReturn(shopUser);
        ShopUser result = shopUserService.findCurrentlyLoggedUser();
        //then
        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(username);
    }
}
