package com.AsadBabayev.services;

import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.job.JobRequestDto;
import com.AsadBabayev.entities.Company;
import com.AsadBabayev.entities.Job;
import com.AsadBabayev.repository.CompanyRepository;
import com.AsadBabayev.repository.JobRepository;
import com.AsadBabayev.services.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceImplUnitTest {

    @Mock
    private JobRepository jobRepository;
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private JobServiceImpl jobService;

    private Company company1;
    private Job job1;
    private Job job2;
    private JobRequestDto jobRequestDto;

    @BeforeEach
    void setUp() {
        company1 = new Company();
        company1.setId(1);
        company1.setName("OpenAi");

        job1 = new Job();
        job1.setId(10);
        job1.setTitle("Software Engineer");
        job1.setCompany(company1);

        job2 = new Job();
        job2.setId(11);
        job2.setTitle("Data Scientist");
        job2.setCompany(company1);

        jobRequestDto = new JobRequestDto();
        jobRequestDto.setTitle("New Job");
        jobRequestDto.setCompanyId(1);
    }

    @Test
    void shouldReturnListOfJobDto() {
        when(jobRepository.findAll()).thenReturn(List.of(job1, job2));
        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));

        List<JobDto> response = jobService.getAllJob();

        assertThat(response).hasSize(2);
        assertThat(response.get(0).getTitle()).isEqualTo("Software Engineer");
        assertThat(response.get(0).getCompanyActionDto().getName()).isEqualTo("OpenAi");
    }

    @Test
    void shouldReturnJobDtoById() {
        when(jobRepository.findById(10)).thenReturn(Optional.of(job1));
        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));

        JobDto response = jobService.getJobById(10);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Software Engineer");
        assertThat(response.getCompanyActionDto().getName()).isEqualTo("OpenAi");
    }

    @Test
    void shouldReturnNullWhenJobNotFound() {
        when(jobRepository.findById(999)).thenReturn(Optional.empty());

        JobDto response = jobService.getJobById(999);

        assertThat(response).isNull();
    }

    @Test
    void shouldSaveJobAndReturnDto() {
        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));
        when(jobRepository.save(any(Job.class))).thenReturn(job1);

        JobDto response = jobService.saveJob(jobRequestDto);

        verify(jobRepository, times(1)).save(any(Job.class));

        assertThat(response.getTitle()).isEqualTo("Software Engineer");
        assertThat(response.getCompanyActionDto().getName()).isEqualTo("OpenAi");
    }

    @Test
    void shouldUpdateJobAndReturnDto() {
        when(jobRepository.findById(10)).thenReturn(Optional.of(job1));
        when(companyRepository.findById(1)).thenReturn(Optional.of(company1));
        when(jobRepository.save(any(Job.class))).thenReturn(job1);

        JobRequestDto updateDto = new JobRequestDto();
        updateDto.setTitle("Updated Title");
        updateDto.setCompanyId(1);

        JobDto response = jobService.updateJob(updateDto, 10);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void shouldDeleteJobById() {
        doNothing().when(jobRepository).deleteById(10);

        jobService.deleteJob(10);

        verify(jobRepository, times(1)).deleteById(10);
    }
}
