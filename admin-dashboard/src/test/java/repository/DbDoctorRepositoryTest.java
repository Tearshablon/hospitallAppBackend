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
public class DbDoctorRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
    public void saveAndFindDoctor() {
        doctorRepository.saveDoctor(new Doctor(null, "Иван", "Иванов", null), null);
        List<Doctor> doctorList = doctorRepository.findAllDoctors();

        Doctor findInsertDoctor = doctorRepository.findDoctor(doctorList.get(0).getId());

        assertThat(doctorList.size()).isEqualTo(1);
        assertThat(findInsertDoctor.getId()).isEqualTo(doctorList.get(0).getId());
        assertThat(findInsertDoctor.getFirstName()).isEqualTo(doctorList.get(0).getFirstName());
        assertThat(findInsertDoctor.getLastName()).isEqualTo(doctorList.get(0).getLastName());
        assertThat(findInsertDoctor.getPhoto()).isEqualTo(doctorList.get(0).getPhoto());

        doctorRepository.saveDoctor(new Doctor(findInsertDoctor.getId(), "Петр", "Петров", null), null);
        doctorList = doctorRepository.findAllDoctors();
        Doctor findUpdateDoctor = doctorRepository.findDoctor(doctorList.get(0).getId());

        assertThat(findUpdateDoctor.getId()).isEqualTo(doctorList.get(0).getId());
        assertThat(findUpdateDoctor.getFirstName()).isEqualTo(doctorList.get(0).getFirstName());
        assertThat(findUpdateDoctor.getLastName()).isEqualTo(doctorList.get(0).getLastName());
        assertThat(findUpdateDoctor.getPhoto()).isEqualTo(doctorList.get(0).getPhoto());
    }

    @Test
    public void findAllDoctors() {

        departmentRepository.saveDepartment(new Department(null, DepartmentName.THERAPY, "описание_1", null));
        List<Department> departmentList = departmentRepository.findAllDepartmentWithoutDoctors();

        doctorRepository.saveDoctor(new Doctor(null, "Иван", "Иванов", null), departmentList.get(0).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Петр", "Петров", null), departmentList.get(0).getId());
        doctorRepository.saveDoctor(new Doctor(null, "Василий", "Васильев", null), departmentList.get(0).getId());

        List<Doctor> doctorList = doctorRepository.findAllDoctors();

        assertThat(doctorList.size()).isEqualTo(3);

        assertThat(doctorList.get(0).getId()).isEqualTo(doctorList.get(0).getId());
        assertThat(doctorList.get(0).getFirstName()).isEqualTo(doctorList.get(0).getFirstName());
        assertThat(doctorList.get(0).getLastName()).isEqualTo(doctorList.get(0).getLastName());
        assertThat(doctorList.get(0).getPhoto()).isEqualTo(doctorList.get(0).getPhoto());

        assertThat(doctorList.get(1).getId()).isEqualTo(doctorList.get(1).getId());
        assertThat(doctorList.get(1).getFirstName()).isEqualTo(doctorList.get(1).getFirstName());
        assertThat(doctorList.get(1).getLastName()).isEqualTo(doctorList.get(1).getLastName());
        assertThat(doctorList.get(1).getPhoto()).isEqualTo(doctorList.get(1).getPhoto());

        assertThat(doctorList.get(2).getId()).isEqualTo(doctorList.get(2).getId());
        assertThat(doctorList.get(2).getFirstName()).isEqualTo(doctorList.get(2).getFirstName());
        assertThat(doctorList.get(2).getLastName()).isEqualTo(doctorList.get(2).getLastName());
        assertThat(doctorList.get(2).getPhoto()).isEqualTo(doctorList.get(2).getPhoto());
    }

    @Test
    public void deleteDoctor() {
        doctorRepository.saveDoctor(new Doctor(null, "Иван", "Иванов", null), null);
        List<Doctor> doctorList = doctorRepository.findAllDoctors();

        Doctor findInsertDoctor = doctorRepository.findDoctor(doctorList.get(0).getId());

        assertThat(doctorList.size()).isEqualTo(1);
        assertThat(findInsertDoctor.getId()).isEqualTo(doctorList.get(0).getId());
        assertThat(findInsertDoctor.getFirstName()).isEqualTo(doctorList.get(0).getFirstName());
        assertThat(findInsertDoctor.getLastName()).isEqualTo(doctorList.get(0).getLastName());
        assertThat(findInsertDoctor.getPhoto()).isEqualTo(doctorList.get(0).getPhoto());

        doctorRepository.deleteDoctor(findInsertDoctor.getId());
        assertThat(doctorList.size()).isEqualTo(1);
    }
}

