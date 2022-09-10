package peaksoft.controller;

import peaksoft.entity.Company;
import peaksoft.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/allCompanies")
    public String getAllCompanies(Model model){
        model.addAttribute("companies",companyService.getAllCompanies());
        return "/companies/mainCompanyPage";
    }

    @GetMapping("/new")
    public String addCompany(Model model){
        Company company = new Company();
        model.addAttribute("newCompany",company);
        return "/companies/newCompany";
    }

    @RequestMapping("/save")
    public String saveCompany(@ModelAttribute("newCompany") Company company){
        companyService.save(company);
        return "redirect:/allCompanies";
    }

    @GetMapping("/edit/{id}")
    public String editCompany(Model model, @PathVariable("id")Long id){
        Company company = companyService.getById(id);
        model.addAttribute("company",company);
        return "companies/updateCompany";
    }

    @PostMapping("/update/{id}")
    public String updateCompany(@PathVariable("id")Long id,@ModelAttribute("company")Company company){
        companyService.updateCompany(id, company);
        return "redirect:/allCompanies";
    }


    @GetMapping("/getById/{id}")
    public String getById(Model model,@PathVariable("id")Long id){
        Company company = companyService.getById(id);
        model.addAttribute("company1",company);
        return "companies/mainCompanyPage";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCompanyById(@PathVariable(name = "id") Long id) {
        companyService.deleteById(id);
        return "redirect:/allCompanies";
    }
}
