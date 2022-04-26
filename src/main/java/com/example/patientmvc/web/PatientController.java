package com.example.patientmvc.web;

import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.repositorises.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "Keyword", defaultValue = "") String Keyword
    ) {
        Page<Patient> PagePatients = patientRepository.findByNomContains(Keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", PagePatients.getContent());
        model.addAttribute("pages", new int[PagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("Keyword", Keyword);

        return "patient";

    }

    @GetMapping("/admin/delete")
    public String delete(Long id, String Keyword ,int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&Keyword="+Keyword;//redirection je suppeime et je revient a index.
    }

    @GetMapping("/")
    public String Home() {
        return "Home";
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient>patientList(){
        return patientRepository.findAll();
    }

    @GetMapping( "/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
              return "formPatients";
    }
    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient , BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,//@R:pour avoir des valeur par defaut
                       @RequestParam(defaultValue = "") String Keyword){ //@valid:on dit a mvc une foit tu stock les donnees dans patient tu fait la validation
       //la collection des errors va stocke dans une collection de type Binding
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
return "redirect:/user/index?page="+page+"&Keyword="+Keyword;
    }

    @GetMapping( "/admin/editePatients")
    public String editePatients(Model model, Long id, String Keyword, int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null)throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("Keyword",Keyword);
        return "editePatients";
    }
}
