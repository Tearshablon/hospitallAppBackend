package repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import domain.Department;
import domain.DepartmentName;
import domain.Doctor;
import interfaces.DepartmentRepository;
import interfaces.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.hospitall.config.RepoConfig;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepoConfig.class})
public class DbDepartmentsRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DoctorRepository doctorRepository;

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Before
    public void doPrepare() {
        Operation ops = sequenceOf(
                deleteAllFrom(
                        "hospitallapp.department",
                        "hospitallapp.doctor"
                )
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), ops);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test
    public void saveDepartment() {
        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание", null));
        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();

        assertThat(departmentList.size()).isEqualTo(1);
        assertThat(departmentList.get(0).getName().getDepartmentName()).isEqualTo(String.valueOf(DepartmentName.THERAPY.getDepartmentName()));
        assertThat(departmentList.get(0).getDescription()).isEqualTo("описание");
        assertThat(departmentList.get(0).getDoctorList()).isNull();
    }

    @Test
    public void findAllDepartments() {
        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание_1", null));
        departmentRepository.saveDepartment(new Department(null, DepartmentName.STOMATOLOGY, "описание_2", null));
        departmentRepository.saveDepartment(new Department(null, DepartmentName.NEUROLOGY, "описание_3", null));

        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();

        assertThat(departmentList.size()).isEqualTo(3);
        assertThat(departmentList.get(0).getName().getDepartmentName()).isEqualTo(String.valueOf(DepartmentName.THERAPY.getDepartmentName()));
        assertThat(departmentList.get(0).getDescription()).isEqualTo("описание_1");
        assertThat(departmentList.get(0).getDoctorList()).isNull();

        assertThat(departmentList.get(1).getName().getDepartmentName()).isEqualTo(String.valueOf(DepartmentName.STOMATOLOGY.getDepartmentName()));
        assertThat(departmentList.get(1).getDescription()).isEqualTo("описание_2");
        assertThat(departmentList.get(1).getDoctorList()).isNull();

        assertThat(departmentList.get(2).getName().getDepartmentName()).isEqualTo(String.valueOf(DepartmentName.NEUROLOGY.getDepartmentName()));
        assertThat(departmentList.get(2).getDescription()).isEqualTo("описание_3");
        assertThat(departmentList.get(2).getDoctorList()).isNull();
    }


    @Test
    public void findDepartmentById() {
        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание", null));
        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();

        Department department = departmentRepository.findDepartmentWithoutDoctors(departmentList.get(0).getId());

        assertThat(department.getId()).isEqualTo(departmentList.get(0).getId());
        assertThat(department.getName()).isEqualTo(departmentList.get(0).getName());
        assertThat(department.getDescription()).isEqualTo(departmentList.get(0).getDescription());
        assertThat(department.getDoctorList()).isEqualTo(departmentList.get(0).getDoctorList());
    }

    @Test
    public void deleteDepartmentById() {
        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание", null));
        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();

        assertThat(departmentList.size()).isEqualTo(1);
        assertThat(departmentList.get(0).getName().getDepartmentName()).isEqualTo(String.valueOf(DepartmentName.THERAPY.getDepartmentName()));
        assertThat(departmentList.get(0).getDescription()).isEqualTo("описание");
        assertThat(departmentList.get(0).getDoctorList()).isNull();

        departmentRepository.deleteDepartment(departmentList.get(0).getId());
        departmentList = departmentRepository.findAllDepartmentWithoutDoctors();
        assertThat(departmentList.size()).isEqualTo(0);
    }

    @Test
    public void findAllDepartmentsWithDoctors() {
        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание_1", null));
        departmentRepository.saveDepartment(new Department(null, DepartmentName.STOMATOLOGY, "описание_2", null));

        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();
        doctorRepository.saveDoctor(new Doctor(null, "Иван", "Иванов", null), departmentList.get(0).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Петр", "Петров", null), departmentList.get(0).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Вася", "Васильев", null), departmentList.get(0).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Катя", "Титова", null), departmentList.get(1).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Остап", "Бендер", null), departmentList.get(1).getId());

        List<Department> departmentListWithDoctors = departmentRepository.findAllDepartmentsWithDoctors();

        assertThat(departmentListWithDoctors.size()).isEqualTo(2);
        assertThat(departmentListWithDoctors.get(0).getName()).isEqualTo(departmentList.get(0).getName());
        assertThat(departmentListWithDoctors.get(0).getDescription()).isEqualTo(departmentList.get(0).getDescription());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().size()).isNotNull();
        assertThat(departmentListWithDoctors.get(1).getName()).isEqualTo(departmentList.get(1).getName());
        assertThat(departmentListWithDoctors.get(1).getDescription()).isEqualTo(departmentList.get(1).getDescription());
        assertThat(departmentListWithDoctors.get(1).getDoctorList().size()).isNotNull();

        List<Doctor> doctorList = doctorRepository.findAllDoctors();
        assertThat(departmentListWithDoctors.get(0).getDoctorList().size()).isEqualTo(3);

        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(0).getFirstName()).isEqualTo(doctorList.get(0).getFirstName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(0).getLastName()).isEqualTo(doctorList.get(0).getLastName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(0).getPhoto()).isEqualTo(doctorList.get(0).getPhoto());

        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(1).getFirstName()).isEqualTo(doctorList.get(1).getFirstName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(1).getLastName()).isEqualTo(doctorList.get(1).getLastName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(1).getPhoto()).isEqualTo(doctorList.get(1).getPhoto());

        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(2).getFirstName()).isEqualTo(doctorList.get(2).getFirstName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(2).getLastName()).isEqualTo(doctorList.get(2).getLastName());
        assertThat(departmentListWithDoctors.get(0).getDoctorList().get(2).getPhoto()).isEqualTo(doctorList.get(2).getPhoto());

        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(0).getFirstName()).isEqualTo(doctorList.get(3).getFirstName());
        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(0).getLastName()).isEqualTo(doctorList.get(3).getLastName());
        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(0).getPhoto()).isEqualTo(doctorList.get(3).getPhoto());

        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(1).getFirstName()).isEqualTo(doctorList.get(4).getFirstName());
        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(1).getLastName()).isEqualTo(doctorList.get(4).getLastName());
        assertThat(departmentListWithDoctors.get(1).getDoctorList().get(1).getPhoto()).isEqualTo(doctorList.get(4).getPhoto());
    }
}

