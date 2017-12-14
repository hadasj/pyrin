package cz.i.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.i.dao.FactValueMapper;


/**
 * @author jan.hadas@i.cz
 */
@Component
public class FactValueCrudService {

    @Autowired
    private FactValueMapper factValueMapper;




}
