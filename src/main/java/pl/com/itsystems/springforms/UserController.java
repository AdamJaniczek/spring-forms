package pl.com.itsystems.springforms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @ResponseBody
    public String list() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            return "Brak użytkowników do wyświetlenia";
        } else {
            String result = "";
            for (User user : userList) {
                result += user.getName() + " " + user.getLastName() + " " + user.getAge() + " lat.<br>";
            }
            return result;
        }
    }

    @RequestMapping("/add")
    public String add(@RequestParam(required = false, defaultValue = "") String imie,
                      @RequestParam(required = false, defaultValue = "") String nazwisko,
                      @RequestParam(required = false, defaultValue = "0") int wiek) {
        if (imie.isEmpty() || nazwisko.isEmpty()) {
            return "redirect:/err.html";
        } else {
            User user = new User(imie, nazwisko, wiek);
            userRepository.add(user);
            return "redirect:/success.html";
        }
    }
}
