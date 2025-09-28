package com.mottu.mapping.web.controller;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.request.UserRequestDTO;
import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.*;
import com.mottu.mapping.api.service.ModelService;
import com.mottu.mapping.api.service.MotoService;
import com.mottu.mapping.api.service.MotoYardService;
import com.mottu.mapping.api.service.SectorService;
import com.mottu.mapping.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MotoYardService motoYardService;

    @Autowired
    private MotoService motoService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, Authentication authentication,
                            @RequestParam(value="page", defaultValue="0") int page) {
        if (authentication == null ||
                authentication.getAuthorities().stream()
                        .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/login?unauthorized";
        }
        Long yardId = 1L;
        MotoYardResponseDTO yardResponseDTO = motoYardService.getById(yardId);
        Pageable pageable = PageRequest.of(page, 10, Sort.sort(MotoResponseDTO.class).by(MotoResponseDTO::getMotorcycleId).ascending());
        Page<MotoResponseDTO> motosPage = motoService.getBySector_YardId(yardId, pageable);
        model.addAttribute("yardMotos", motosPage.getContent());
        model.addAttribute("yard", yardResponseDTO);
        model.addAttribute("totalPages", motosPage.getTotalPages());
        model.addAttribute("currentPage", motosPage.getNumber());
        return "admin/dashboard";
    }

    @GetMapping("/admin/cadastra-moto")
    public String cadastraMoto(Model model) {
        model.addAttribute("moto", new MotoRequestDTO());
        List<ModelResponseDTO> models = modelService.getAll();
        model.addAttribute("models", models);
        // retorna setores de um yard específico (id = 1)
        List<SectorResponseDTO> sectors = sectorService.getByYardId(1L);
        model.addAttribute("sectors", sectors);
        return "admin/cadastra-moto";
    }

    @GetMapping("/admin/cadastra-setor")
    public String cadastraSetor(Model model) {
        model.addAttribute("sector", new SectorRequestDTO());
        List<SectorResponseDTO> sectors = sectorService.getByYardId(1L);
        model.addAttribute("sectors", sectors);
        return "admin/cadastra-setor";
    }

    @GetMapping("/admin/users")
    public String manageUsers(Model model, Authentication authentication) {
        if (authentication == null ||
                authentication.getAuthorities().stream()
                        .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/login?unauthorized";
        }
        List<UserResponseDTO> users = userService.getOperatorUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/admin/cadastra-operador")
    public String cadastraOperadorForm(Model model) {
        model.addAttribute("user", new UserRequestDTO());
        return "admin/cadastra-operador";
    }

    @PostMapping("/admin/cadastra-operador")
    public String cadastraOperador(@ModelAttribute UserRequestDTO userDto, BindingResult result, Model model, Authentication authentication) {
        if (authentication == null ||
                authentication.getAuthorities().stream()
                        .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/login?unauthorized";
        }
        if (result.hasErrors()) {
            return "admin/cadastra-operador";
        }
        userService.saveOperator(userDto);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/cadastra-setor")
    public String cadastraSetor(@ModelAttribute SectorRequestDTO sectorDto) {
        sectorDto.setYardId(1L); // sempre vincula ao yard 1
        sectorService.save(sectorDto);
        return "redirect:/admin/cadastra-setor";
    }

    @PostMapping("/admin/cadastra-moto")
    public String cadastraMoto(@ModelAttribute MotoRequestDTO motoRequestDTO) {
        motoService.save(motoRequestDTO);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/moto-details/{id}")
    public String motoDetails(@PathVariable Long id, Model model) {
        MotoResponseDTO motoResp = motoService.getById(id);
        MotoRequestDTO moto = new MotoRequestDTO();
        moto.setPlate(motoResp.getPlate());
        moto.setCoordinates(motoResp.getCoordinates());
        if (motoResp.getModel() != null) {
            moto.setModelId(motoResp.getModel().getModelId());
        }
        if (motoResp.getSector() != null) {
            moto.setSectorId(motoResp.getSector().getSectorId());
        }
        model.addAttribute("moto", moto);
        model.addAttribute("models", modelService.getAll());
        model.addAttribute("sectors", sectorService.getByYardId(1L));
        model.addAttribute("id", id);
        return "admin/moto-details";
    }

    @PostMapping("/admin/deleta-moto/{id}")
    public String deletaMoto(@PathVariable("id") Long id) {
        motoService.delete(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/moto-details/{id}")
    public String updateMoto(@PathVariable Long id,
                        @Valid @ModelAttribute MotoRequestDTO motoRequestDTO,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", motoRequestDTO);
            model.addAttribute("id", id);
            model.addAttribute("models", modelService.getAll());
            model.addAttribute("sectors", sectorService.getByYardId(1L));
            model.addAttribute("editMotoMsg", "Erro ao atualizar moto. Verifique os campos.");
            return "admin/moto-details";
        }
        motoService.update(id, motoRequestDTO);
        model.addAttribute("editMotoMsg", "Moto atualizada com sucesso!");
        return "redirect:/admin/moto-details/" + id;
    }

    @GetMapping("/admin/edita-setor/{id}")
    public String editaSetorForm(@PathVariable("id") Long id, Model model) {
        SectorResponseDTO sector = sectorService.getById(id);
        model.addAttribute("sector", sector);
        return "admin/edita-setor";
    }

    @PostMapping("/admin/edita-setor/{id}")
    public String editaSetor(@PathVariable("id") Long id, @ModelAttribute SectorRequestDTO sectorDto) {
        sectorDto.setYardId(1L);
        sectorService.update(id, sectorDto);
        return "redirect:/admin/cadastra-setor";
    }

    @PostMapping("/admin/deleta-setor/{id}")
    public String deletaSetor(@PathVariable("id") Long id) {
        sectorService.delete(id);
        return "redirect:/admin/cadastra-setor";
    }

    @PostMapping("/admin/deleta-user/{userId}")
    public String deletaUser(@PathVariable Long userId, Authentication authentication) {
        if (authentication == null ||
            authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/login?unauthorized";
        }
        userService.deleteOperator(userId);
        return "redirect:/admin/users";
    }
}
