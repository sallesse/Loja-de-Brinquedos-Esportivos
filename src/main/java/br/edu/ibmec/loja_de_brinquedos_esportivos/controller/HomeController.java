package br.edu.ibmec.loja_de_brinquedos_esportivos.controller;

import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Brinquedo;
import br.edu.ibmec.loja_de_brinquedos_esportivos.service.BrinquedoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final BrinquedoService service;

    public HomeController(BrinquedoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/painel")
    public String painel(Model model, Authentication authentication) {
        String perfil = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.replace("ROLE_", ""))
                .findFirst()
                .orElse("");

        model.addAttribute("usuario", authentication.getName());
        model.addAttribute("perfil", perfil);
        model.addAttribute("admin", "ADMIN".equals(perfil));
        model.addAttribute("brinquedos", service.listar());
        return "painel";
    }

    @PostMapping("/painel/brinquedos")
    public String cadastrar(@Valid @ModelAttribute Brinquedo brinquedo,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro",
                    result.getAllErrors().get(0).getDefaultMessage());
        } else {
            service.criar(brinquedo);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Brinquedo cadastrado com sucesso.");
        }

        return "redirect:/painel";
    }
}