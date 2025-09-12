package com.AsadBabayev.services;

import com.AsadBabayev.dto.company.CompanyDto;
import com.AsadBabayev.dto.company.CompanyRequestDto;
import com.AsadBabayev.entities.Company;
import com.AsadBabayev.entities.Job;
import com.AsadBabayev.repository.CompanyRepository;
import com.AsadBabayev.repository.JobRepository;
import com.AsadBabayev.services.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplUnitTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private CompanyServiceImpl companyService;
    private CompanyRequestDto requestDto;
    private Company company1;
    private Company company2;
    private Job job1;
    private Job job2;

    @BeforeEach
    void setUp() {

        company1 = new Company();
        company1.setId(1);
        company1.setName("OpenAi");
        company1.setDescription("AI research");
        company1.setLocation("Krakow");
        company1.setEmail("contact@openai.com");
        company1.setPhone("+123456789");

        company2 = new Company();
        company2.setId(2);
        company2.setName("DeepMind");
        company2.setDescription("Deep learning research lab");
        company2.setLocation("London");
        company2.setEmail("contact@deepmind.com");
        company2.setPhone("+987654321");

        job1 = new Job();
        job1.setId(10);
        job1.setTitle("Software Engineer");
        job1.setDescription("Work on AI systems");
        job1.setRequirements("Java, Spring Boot, SQL");
        job1.setLocation("Krakow");
        job1.setSalaryRange("10k-15k PLN");
        job1.setActive(true);
        job1.setCompany(company1);

        job2 = new Job();
        job2.setId(11);
        job2.setTitle("Data Scientist");
        job2.setDescription("Analyze datasets and build ML models");
        job2.setRequirements("Python, TensorFlow, SQL");
        job2.setLocation("London");
        job2.setSalaryRange("12k-18k PLN");
        job2.setActive(true);
        job2.setCompany(company1);

        requestDto = new CompanyRequestDto();
        BeanUtils.copyProperties(company1, requestDto);
    }

    @Test
    void shouldReturnCompanyDtoWithJobsForGivenId() {

        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));
        when(jobRepository.findByCompanyId(1)).thenReturn(List.of(job1,job2));

        CompanyDto response = companyService.getCompanyById(1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("OpenAi");

        assertThat(response.getJobActionDtoList().get(0).getTitle()).isEqualTo("Software Engineer");
        assertThat(response.getJobActionDtoList().get(0).getDescription()).isEqualTo("Work on AI systems");
        assertThat(response.getJobActionDtoList().get(0).getRequirements()).isEqualTo("Java, Spring Boot, SQL");

        assertThat(response.getJobActionDtoList().get(1).getTitle()).isEqualTo("Data Scientist");
        assertThat(response.getJobActionDtoList().get(1).getDescription()).isEqualTo("Analyze datasets and build ML models");
        assertThat(response.getJobActionDtoList().get(1).getRequirements()).isEqualTo("Python, TensorFlow, SQL");
    }

    @Test
    void shouldReturnListOfCompanyDto(){
        when(companyRepository.findAll()).thenReturn(List.of(company1,company2));
        when(jobRepository.findByCompanyId(1)).thenReturn(List.of(job1,job2));
        when(jobRepository.findByCompanyId(2)).thenReturn(List.of());

       List<CompanyDto> response = companyService.getAllCompany();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);

        CompanyDto dto1 = response.get(0);
        assertThat(dto1.getId()).isEqualTo(1);
        assertThat(dto1.getName()).isEqualTo("OpenAi");
        assertThat(dto1.getJobActionDtoList()).hasSize(2);

        assertThat(dto1.getJobActionDtoList().get(0).getTitle()).isEqualTo("Software Engineer");
        assertThat(dto1.getJobActionDtoList().get(1).getTitle()).isEqualTo("Data Scientist");

        CompanyDto dto2 = response.get(1);
        assertThat(dto2.getId()).isEqualTo(2);
        assertThat(dto2.getName()).isEqualTo("DeepMind");
        assertThat(dto2.getJobActionDtoList()).isEmpty();
    }

    @Test
    void shouldReturnNullWhenCompanyNotFound() {
        when(companyRepository.findById(999)).thenReturn(Optional.empty());

        CompanyDto response = companyService.getCompanyById(999);

        assertThat(response).isNull();
    }

    @Test
    void shouldSaveCompanyAndReturnDto() {
        when(companyRepository.save(any(Company.class))).thenReturn(company1);

        CompanyDto response = companyService.saveCompany(requestDto);

        verify(companyRepository, times(1)).save(any(Company.class));

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("OpenAi");
        assertThat(response.getDescription()).isEqualTo("AI research");
        assertThat(response.getLocation()).isEqualTo("Krakow");
        assertThat(response.getEmail()).isEqualTo("contact@openai.com");
        assertThat(response.getPhone()).isEqualTo("+123456789");
    }

    @Test
    void shouldUpdateCompanyAndReturnCompanyDto() {
        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));
        when(companyRepository.save(any(Company.class))).thenReturn(company1);

        CompanyRequestDto updateDto = new CompanyRequestDto();
        updateDto.setName("Updated Name");

        CompanyDto response = companyService.updateCompany(updateDto, 1);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Updated Name");
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistentCompany() {
        when(companyRepository.findById(999)).thenReturn(Optional.empty());

        CompanyRequestDto updateDto = new CompanyRequestDto();
        updateDto.setName("Doesn't matter");

        CompanyDto response = companyService.updateCompany(updateDto, 999);

        assertThat(response).isNull();
    }

    @Test
    void shouldDeleteCompanyById() {
        doNothing().when(companyRepository).deleteById(1);

        companyService.deleteCompany(1);

        verify(companyRepository, times(1)).deleteById(1);
    }
}
