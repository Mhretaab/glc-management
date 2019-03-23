package com.glc.managment.department;

import com.glc.managment.member.Member;
import com.glc.managment.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mberhe on 1/1/19.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private ActivityRepository activityRepository;
    private MemberRepository memberRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ActivityRepository activityRepository,
                                 MemberRepository memberRepository) {
        this.departmentRepository = departmentRepository;
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Department addDepartment(Department department) {
        if(department == null)
            return null;
        Department persistedDepartment = departmentRepository.save(department);
        return persistedDepartment;
    }

    @Override
    public Department updateDepartment(Department department) {
        if(department == null)
            return null;
        Department persistedDepartment = departmentRepository.save(department);
        return persistedDepartment;
    }

    @Override
    public Boolean deleteDepartment(Department department) {
        if(department == null)
            return true;
        departmentRepository.delete(department);
        return departmentRepository.findByUuid(department.getUuid()) != null?false:true;
    }

    @Override
    public Boolean deleteDepartmentByUuid(String departmentUuid) {
        if(departmentUuid == null || departmentUuid.isEmpty())
            return true;
        departmentRepository.deleteByUuid(departmentUuid);
        return departmentRepository.findByUuid(departmentUuid) != null?false:true;
    }

    @Override
    public Department findDepartmentByUuid(String departmentUuid) {
        if(departmentUuid == null || departmentUuid.isEmpty())
            return null;

        return departmentRepository.findByUuid(departmentUuid);
    }

    @Override
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Page<Department> findAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Department addActivityToDepartment(String departmentUuid, Activity activity) {
        Department department = departmentRepository.findByUuid(departmentUuid);
        List<Activity> activities = activityRepository.findByDepartment(department);

        Boolean activityExists = false;
        for (Activity a: activities) {
            if(a.getName().equalsIgnoreCase(activity.getName())){
                activityExists = true;
                break;
            }
        }
        if(!activityExists){
            activity.setDepartment(department);
            activityRepository.save(activity);
        }
        return departmentRepository.findByUuid(departmentUuid);
    }

    @Override
    public List<Activity> findAllDepartmentActivities(Department department) {
        if(null == department)
            return null;
        return activityRepository.findByDepartment(department);
    }

    @Override
    public List<Activity> findAllDepartmentActivities(String departmentUuid) {
        if(null == departmentUuid || departmentUuid.isEmpty())
            return null;

        return findAllDepartmentActivities(departmentRepository.findByUuid(departmentUuid));
    }

    @Override
    public List<Member> findAllDepartmentMembers(Department department) {
        if(null == department)
            return null;
        List<Member> members = memberRepository.findAllByDepartments(new ArrayList<Department>(){{add(department);}});
        return members;
    }

    @Override
    public List<Member> findAllDepartmentMembers(String departmentUuid) {
        if(null == departmentUuid || departmentUuid.isEmpty())
            return null;
        return findAllDepartmentMembers(departmentRepository.findByUuid(departmentUuid));
    }
}
