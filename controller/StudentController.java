package com.example.app_jpa_relationships.controller;

import com.example.app_jpa_relationships.entity.Address;
import com.example.app_jpa_relationships.entity.Group;
import com.example.app_jpa_relationships.entity.Student;
import com.example.app_jpa_relationships.entity.Subject;
import com.example.app_jpa_relationships.payload.StudentDto;
import com.example.app_jpa_relationships.repository.AddressRepository;
import com.example.app_jpa_relationships.repository.GroupRepository;
import com.example.app_jpa_relationships.repository.StudentRepository;
import com.example.app_jpa_relationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                  @RequestParam int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(facultyId, pageable);
        return studentPage;
    }

    @GetMapping("/forGroup/{groupId}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer groupId,
                                                @RequestParam int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroupId(groupId, pageable);
        return studentPage;
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Address address = student.getAddress();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        addressRepository.save(address);
//        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
//        if (!optionalAddress.isPresent()) return "The address with current id does not exist!";
//        student.setAddress(addressRepository.getOne(studentDto.getAddressId()));
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) return "The group with current id does not exist!";
        student.setGroup(groupRepository.getOne(studentDto.getGroupId()));
        List<Integer> subjects = studentDto.getSubjects();
        List<Subject> foundSubjects = new ArrayList<>();
        String notFoundSubjects = "";
        for (Integer subject : subjects) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subject);
            if (!optionalSubject.isPresent()) notFoundSubjects += subject + ", ";
            foundSubjects.add(optionalSubject.get());
        }
        student.setSubjects(foundSubjects);
        studentRepository.save(student);
        return "student saved successfully!";
    }

    @PutMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) return "The student with current id not found!";
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Address address = student.getAddress();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        addressRepository.save(address);
//        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
//        if (!optionalAddress.isPresent()) return "The address with current id does not exist!";
//        student.setAddress(addressRepository.getOne(studentDto.getAddressId()));
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) return "The group with current id does not exist!";
        student.setGroup(groupRepository.getOne(studentDto.getGroupId()));
        List<Integer> subjects = studentDto.getSubjects();
        List<Subject> foundSubjects = new ArrayList<>();
        String notFoundSubjects = "";
        for (Integer subject : subjects) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subject);
            if (!optionalSubject.isPresent()) notFoundSubjects += subject + ", ";
            foundSubjects.add(optionalSubject.get());
        }
        student.setSubjects(foundSubjects);
        studentRepository.save(student);
        return "student edited successfully!";
    }

    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) return "The student with current id not found!";
        addressRepository.delete(optionalStudent.get().getAddress());
        studentRepository.delete(optionalStudent.get());
        return "chosen student deleted successfully!";
    }
}
