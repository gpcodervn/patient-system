package com.gpcoder.repository.impl;

import com.gpcoder.entity.Patient;
import com.gpcoder.model.request.PatientFilterable;
import com.gpcoder.repository.CustomPatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class CustomPatientRepositoryImpl extends BaseRepositoryImpl<Patient, UUID> implements CustomPatientRepository {

    private static final String[] SORTED_FIELDS = {"firstName", "lastName", "gender", "patientId", "dob"};
    private static final String SORT_ASC = "+";
    private static final String SORT_DESC = "-";

    @Autowired
    public CustomPatientRepositoryImpl(EntityManager em){
        super(Patient.class, em);
    }

    @Override
    public Page<Patient> findAll(PatientFilterable filterable) {
        Specification<Patient> specification = buildSpecification(filterable);
        Sort sort = buildMultipleSort(filterable.getSort());
        Pageable pageable = PageRequest.of(filterable.getPage(), filterable.getLimit(), sort);
        return super.findAll(specification, pageable);
    }

    private Sort buildMultipleSort(String[] params) {
        if (ArrayUtils.isEmpty(params)){
            return Sort.unsorted();
        }

        List<Sort> sorts = Arrays.stream(params).map(this::buildSorts)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (sorts.isEmpty()) {
            return Sort.unsorted();
        }
        Sort sort = sorts.get(0);
        for (int i = 1; i < sorts.size(); i++) {
            sort.and(sorts.get(i));
        }
        return sort;
    }

    @Nullable
    private Sort buildSorts(String param) {
        if(StringUtils.isBlank(param)) {
            return null;
        }
        param = param.trim();
        if (param.startsWith(SORT_DESC)) {
            String columnName = param.substring(1);
            if (StringUtils.equalsAnyIgnoreCase(columnName, SORTED_FIELDS)) {
                return Sort.by(columnName).descending();
            }
        } else {
            String columnName = param;
            if (param.startsWith(SORT_ASC)) {
                columnName = param.substring(1);
            }
            if (StringUtils.equalsAnyIgnoreCase(columnName, SORTED_FIELDS)) {
                return Sort.by(columnName).ascending();
            }
        }
        return null;
    }

    private Specification<Patient> buildSpecification(PatientFilterable filterable) {
        return (root, query, cb) -> {
            Predicate[] predicates = buildRestrictions(root, cb, filterable);
            if (predicates.length == 0){
                if (BooleanUtils.isNotTrue(filterable.getWithDeleted())) {
                    return cb.and(new QueryBuilder<>(cb, root).addEquals("softDeleted", false).build());
                }
                return null;
            }
            if (BooleanUtils.isNotTrue(filterable.getWithDeleted())) {
                return cb.and(cb.or(predicates), cb.or(new QueryBuilder<>(cb, root).addEquals("softDeleted", false).build()));
            } else {
                return cb.or(predicates);
            }
        };
    }

    private Predicate[] buildRestrictions(Root<Patient> patientRoot, CriteriaBuilder cb, PatientFilterable filterable) {
        QueryBuilder queryBuilder = new QueryBuilder<>(cb, patientRoot)
                .addLikeIgnoreCase("firstName", filterable.getFirstName())
                .addLikeIgnoreCase("lastName", filterable.getLastName())
                .addEqualsIgnoreCase("gender", filterable.getGender())
                .addEqualsIgnoreCase("patientId", filterable.getPatientId())
                .addEquals("dob", filterable.getDob());
        return queryBuilder.build();
    }

    static class QueryBuilder<T> {

        private CriteriaBuilder cb;

        private Root<T> root;

        private List<Predicate> predicates = new ArrayList<>();

        QueryBuilder(CriteriaBuilder cb, Root<T> root) {
            this.cb = cb;
            this.root = root;
        }

        QueryBuilder addLikeIgnoreCase(String propertyPath, String value) {
            if (StringUtils.isNotBlank(value)) {
                predicates.add(cb.like(cb.upper(root.get(propertyPath)), "%" + StringUtils.upperCase(value) + "%"));
            }
            return this;
        }

        QueryBuilder addEqualsIgnoreCase(String propertyPath, String value) {
            if (StringUtils.isNotBlank(value)) {
                predicates.add(cb.equal(cb.upper(root.get(propertyPath)), StringUtils.upperCase(value)));
            }
            return this;
        }

        QueryBuilder addEquals(String propertyPath, Object value) {
            if (value != null && StringUtils.isNotBlank(value.toString())) {
                predicates.add(cb.equal(root.get(propertyPath), value));
            }
            return this;
        }

        Predicate[] build() {
            return predicates.toArray(new Predicate[0]);
        }
    }

}
