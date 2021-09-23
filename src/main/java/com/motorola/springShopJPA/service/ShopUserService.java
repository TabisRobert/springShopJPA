package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ShopUserDto;
import com.motorola.springShopJPA.model.entity.ShopUser;
import com.motorola.springShopJPA.model.enums.UserRole;
import com.motorola.springShopJPA.repository.ShopUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopUserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ShopUserRepository shopUserRepository;

    @Autowired
    public ShopUserService(ModelMapper modelMapper, PasswordEncoder bCryptPasswordEncoder, ShopUserRepository shopUserRepository) {
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.shopUserRepository = shopUserRepository;
    }

    public void createNewUser(ShopUserDto shopUserDto) {
        prepareUserRole(shopUserDto);
        validateUser(shopUserDto);
        String passwordHash = bCryptPasswordEncoder.encode(shopUserDto.getPassword());
        ShopUser shopUser = modelMapper.map(shopUserDto, ShopUser.class);
        shopUser.setPassword(passwordHash);
        shopUser.setEnabled(true);
        shopUserRepository.save(shopUser);
    }

    public List<ShopUserDto> getAllUsers(){
        ShopUser loggedUser = findCurrentlyLoggedUser();
        List<ShopUser> users = (List<ShopUser>) shopUserRepository.findAll();
        List<ShopUserDto> userDtos = new ArrayList<>();
        for (ShopUser shopUser: users){
            if (shopUser.getId().equals(loggedUser.getId())){
                continue;
            }
            ShopUserDto userDto = getShopUserDto(shopUser);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    private ShopUserDto getShopUserDto(ShopUser shopUser) {
        return modelMapper.map(shopUser, ShopUserDto.class);
    }

    public ShopUserDto getDtoOfCurrentlyLoggedUser(){
        return getShopUserDto(findCurrentlyLoggedUser());
    }

    private ShopUser findCurrentlyLoggedUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return shopUserRepository.findByEmail(username);
    }


    private void validateUser(ShopUserDto shopUserDto) {
        if(emailExistInDatabase(shopUserDto)){
            throw new RuntimeException("Login already exist");
        }
    }

    private boolean emailExistInDatabase(ShopUserDto shopUserDto) {
        return !(shopUserRepository.findByEmail(shopUserDto.getEmail()) == null);
    }

    private void prepareUserRole(ShopUserDto shopUserDto) {
        if (isAdminRole()) {
            shopUserDto.setRole(UserRole.ROLE_ADMIN.toString());
        } else {
            shopUserDto.setRole(UserRole.ROLE_USER.toString());
        }
    }

    private boolean isAdminRole() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toArray()[0].toString()
                .equals(UserRole.ROLE_ADMIN.toString());
    }
}
