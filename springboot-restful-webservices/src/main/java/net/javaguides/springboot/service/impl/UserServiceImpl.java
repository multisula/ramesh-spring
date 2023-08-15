package net.javaguides.springboot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public UserDto createUser(UserDto userDto) {
    // 1. Convert UserDto to User Entity
    // Custom UserMapper class
//    User user = UserMapper.mapToUser(userDto);
    // ModelMapper Library
//    User user = modelMapper.map(userDto, User.class);
    // MapStruct Library
    User user = AutoUserMapper.MAPPER.mapToUser(userDto);

    // 2. create user
    User savedUser = userRepository.save(user);

    // 3. Convert User Entity to UserDto
//    userDto = UserMapper.mapToUserDto(savedUser);
//    userDto = modelMapper.map(savedUser, UserDto.class);
    userDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);

    return userDto;
  }

  @Override
  public UserDto getUserById(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
//    return UserMapper.mapToUserDto(optionalUser.get());
//    return modelMapper.map(optionalUser.get(), UserDto.class);
    return AutoUserMapper.MAPPER.mapToUserDto(optionalUser.get());
  }

  @Override
  public List<UserDto> getAllUsers() {
//    return userRepository.findAll().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
//    return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    return userRepository.findAll().stream().map(user -> AutoUserMapper.MAPPER.mapToUserDto(user)).collect(Collectors.toList());
  }


  @Override
  @Transactional
  public UserDto updateUser(UserDto userDto) {
    User existingUser = userRepository.findById(userDto.getId()).get();
    existingUser.setFirstName(userDto.getFirstName());
    existingUser.setLastName(userDto.getLastName());
    existingUser.setEmail(userDto.getEmail());
//    return UserMapper.mapToUserDto(existingUser);
//    return modelMapper.map(existingUser, UserDto.class);
    return AutoUserMapper.MAPPER.mapToUserDto(existingUser);
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }
}
