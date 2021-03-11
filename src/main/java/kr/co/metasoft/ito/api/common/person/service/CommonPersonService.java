package kr.co.metasoft.ito.api.common.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.metasoft.ito.common.entity.jpa.PersonEntity;
import kr.co.metasoft.ito.common.repository.jpa.PersonRepository;

@Service
public class CommonPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Page<PersonEntity> getPersonList(
            PersonEntity personEntity,
            Pageable pageable) {
        return personRepository.findAll(Example.of(personEntity) , pageable);
    }

    @Transactional
    public PersonEntity getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public PersonEntity createPerson(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Transactional
    public PersonEntity modifyPerson(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Transactional
    public void removePerson(Long id) {
        personRepository.deleteById(id);
    }

}