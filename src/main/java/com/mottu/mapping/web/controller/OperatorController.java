package com.mottu.mapping.web.controller;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.service.ModelService;
import com.mottu.mapping.api.service.MotoService;
import com.mottu.mapping.api.service.MotoYardService;
import com.mottu.mapping.api.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class OperatorController {

    @Autowired
    private MotoYardService motoYardService;

    @Autowired
    private MotoService motoService;

    @Autowired
    private ModelService modelService;
    @Autowired
    private SectorService sectorService;

    // trás dashboard do pátio ID 1, voltado para o usuário tipo operador.
    @GetMapping("/operator/dashboard")
    public String dashboard(Model model, Authentication authentication,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        if (authentication == null ||
                authentication.getAuthorities().stream()
                        .noneMatch(a -> a.getAuthority().equals("ROLE_OPERATOR"))) {
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
        return "operator/dashboard"; // Thymeleaf template
    }

    @GetMapping("/operator/cadastra-moto")
    public String cadastraMoto(Model model) {
        model.addAttribute("moto", new MotoRequestDTO());
        List<ModelResponseDTO> models = modelService.getAll();
        model.addAttribute("models", models);
        // retorna setores de um yard específico (id = 1)
        List<SectorResponseDTO> sectors = sectorService.getByYardId(1L);
        model.addAttribute("sectors", sectors);
        return "operator/cadastra-moto";
    }

    @PostMapping("/operator/cadastra-moto")
    public String cadastraMoto(@ModelAttribute MotoRequestDTO motoRequestDTO) {
        motoService.save(motoRequestDTO);
        return "redirect:/operator/dashboard";
    }

    @GetMapping("/operator/moto-details/{id}")
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
        return "operator/moto-details";
    }

    // atualizar moto
    @PostMapping("/operator/moto-details/{id}")
    public String updateMoto(@PathVariable Long id,
                             @Valid @ModelAttribute MotoRequestDTO motoRequestDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", motoRequestDTO); // mantém os dados preenchidos pelo usuário
            model.addAttribute("id", id); // passa o id para o template
            model.addAttribute("models", modelService.getAll());
            model.addAttribute("sectors", sectorService.getByYardId(1L));
            model.addAttribute("editMotoMsg", "Erro ao atualizar moto. Verifique os campos.");
            return "operator/moto-details";
        }
        motoService.update(id, motoRequestDTO);
        model.addAttribute("editMotoMsg", "Moto atualizada com sucesso!");
        return "redirect:/operator/moto-details/" + id;
    }

    @PostMapping("/operator/deleta-moto/{id}")
    public String deletaMoto(@PathVariable("id") Long id) {
        motoService.delete(id);
        return "redirect:/operator/dashboard";
    }
}
