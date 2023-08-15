package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    UserDto savedUser = userService.createUser(userDto);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
    UserDto userDto = userService.getUserById(userId);
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<UserDto> getAllUsers() {
    return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
  }

  @PutMapping("{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                         @RequestBody UserDto userDto) {
    userDto.setId(userId);
    UserDto updatedUser = userService.updateUser(userDto);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
    userService.deleteUser(userId);
    return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
  }
}
