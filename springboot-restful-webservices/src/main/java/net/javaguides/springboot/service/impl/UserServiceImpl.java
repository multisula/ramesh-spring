package net.javaguides.springboot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
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

  @Override
  public UserDto createUser(UserDto userDto) {
    User user = UserMapper.mapToUser(userDto);
    User savedUser = userRepository.save(user);
    userDto = UserMapper.mapToUserDto(savedUser);
    return userDto;
  }

  @Override
  public UserDto getUserById(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    return UserMapper.mapToUserDto(optionalUser.get());
  }

  @Override
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public UserDto updateUser(UserDto userDto) {
    User existingUser = userRepository.findById(userDto.getId()).get();
    existingUser.setFirstName(userDto.getFirstName());
    existingUser.setLastName(userDto.getLastName());
    existingUser.setEmail(userDto.getEmail());
    return UserMapper.mapToUserDto(existingUser);
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }
}
