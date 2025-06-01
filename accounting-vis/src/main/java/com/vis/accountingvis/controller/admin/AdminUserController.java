package com.vis.accountingvis.controller.admin;

import com.vis.accountingvis.dto.UserResponseDTO;
import com.vis.accountingvis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsersForAdmin();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer uid) {
        boolean deleted = userService.deleteUserForAdmin(uid);
        if (deleted) {
            return ResponseEntity.ok("用户删除成功 (uid: " + uid + ")");
        } else {
            return ResponseEntity.status(404).body("用户 (uid: " + uid + ") 不存在或删除失败");
        }
    }
}