package arquitec.test.controller;

import arquitec.test.service.MesaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final MesaService mesaService;

    public WebController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/mesero/dashboard")
    public String meseroDashboard(Model model) {
        model.addAttribute("mesasDisponibles", mesaService.obtenerMesasDisponibles());
        return "mesero-dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/cocina")
    public String cocina() {
        return "cocina";
    }
}
