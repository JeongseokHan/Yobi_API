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

    @PostMapping(value = "/user/1")
    public ResponseEntity<?> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.status(userService.singIn(signInDTO)).build();
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<?> delete(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.status(userService.delete(signInDTO)).build();
    }

}
