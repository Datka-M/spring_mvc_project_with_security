package peaksoft.service;

import peaksoft.entity.Company;
import peaksoft.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public void save(Company company) {
        companyRepository.save(company);
    }

    public void deleteById(Long id) {
        Company company = companyRepository.getById(id);
        companyRepository.delete(company);
    }

    public Company getById(Long id) {
        return companyRepository.findById(id).
                orElseThrow(() -> new NullPointerException("Company with " + id + " was not found"));
    }
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public void updateCompany(Long id, Company company){
        Company company1 = companyRepository.getById(id);
        company1.setCompanyName(company.getCompanyName());
        company1.setLocatedCountry(company.getLocatedCountry());
        companyRepository.save(company1);
    }
}
