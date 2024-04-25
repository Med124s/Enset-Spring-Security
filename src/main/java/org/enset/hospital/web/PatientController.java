package org.enset.hospital.web;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.enset.hospital.entities.Patient;
import org.enset.hospital.service.HospitalServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private HospitalServiceImp hospitalServiceImp;
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Patient> pagePatients = hospitalServiceImp.findPatientsByMc(kw, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String editPatients(Model model,Long Id,
                               @RequestParam(name = "keyword",defaultValue = "") String keyword,
                               @RequestParam(name = "page", defaultValue = "0")int page){
        Patient patient = hospitalServiceImp.patient(Id);
        if(patient == null)
            throw new RuntimeException("Patient Introuvable");
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("patient",patient);
        return "editPatient";
    }
    @PostMapping("/admin/save")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String savePatient(@RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0")int page,
            @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formPatients";
        }
        hospitalServiceImp.AddPatient(patient);
        return "redirect:/user/index?keyword="+keyword+"&page="+page;
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete( @RequestParam(name = "keyword", defaultValue = "") String keyword,
                          @RequestParam(name = "page", defaultValue = "0")int page,
                          Long Id){
        hospitalServiceImp.DeletePatient(Id);
        return "redirect:/user/index?keyword="+keyword+"&page="+page;
    }
}