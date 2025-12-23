package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.ApplicationUser;
import com.bookstorevn.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private ApplicationUserRepository userRepository;

    @GetMapping("")
    public String index() {
        return "admin/user/index";
    }

    @GetMapping("/getall")
    @ResponseBody
    public Map<String, Object> getAll() {
        List<ApplicationUser> users = userRepository.findAll();
        List<Map<String, Object>> data = users.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("name", u.getName());
            map.put("email", u.getEmail());
            map.put("phoneNumber", u.getPhoneNumber() != null ? u.getPhoneNumber() : "");
            map.put("role", u.getRole() != null ? u.getRole() : "Khách hàng");
            map.put("lockoutEnd", u.getLockoutEnd());
            map.put("isLocked", u.isLocked());
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return result;
    }

    @PostMapping("/lockunlock")
    @ResponseBody
    public Map<String, Object> lockUnlock(@RequestBody String id) {
        // id is sent as plain text in C# code, let's fix it if it's sent as JSON
        // Actually, C# code [FromBody] string id might mean just the string.
        // Let's assume it's sent as a JSON string for now or adjust it.
        
        // Remove quotes if present
        id = id.replace("\"", "");

        ApplicationUser user = userRepository.findById(id).orElse(null);
        Map<String, Object> result = new HashMap<>();
        if (user == null) {
            result.put("success", false);
            result.put("message", "Không tìm thấy người dùng");
            return result;
        }

        if (user.isLocked()) {
            user.setLockoutEnd(LocalDateTime.now());
        } else {
            user.setLockoutEnd(LocalDateTime.now().plusYears(1000));
        }

        userRepository.save(user);
        result.put("success", true);
        result.put("message", "Thao tác thành công");
        return result;
    }
}
