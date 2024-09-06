package org.example.yobiapi.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.user.dto.SignInDTO;
import org.example.yobiapi.user.dto.UpdateUserNickNameDTO;
import org.example.yobiapi.user.dto.UserDTO;
import org.example.yobiapi.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(userService.signUp(userDTO)).build();
    }

    @PutMapping(value = "/user")
    public ResponseEntity<?> updateByUserNickName(@RequestBody UpdateUserNickNameDTO updateUserNickNameDTO) {
        return ResponseEntity.status(userService.UpdateByUserNickName(updateUserNickNameDTO)).build();
    }

    @GetMapping(value = "/user/{userId}/{socialType}")
    public ResponseEntity<?> signIn(@PathVariable String userId, @PathVariable String socialType) {
        return ResponseEntity.status(userService.singIn(userId, socialType)).build();
    }

    @DeleteMapping(value = "/user/{userId}/{socialType}")
    public ResponseEntity<?> delete(@PathVariable String userId, @PathVariable String socialType) {
        return ResponseEntity.status(userService.delete(userId, socialType)).build();
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.status(200).body(userService.getUserProfile(userId));
    }

}
