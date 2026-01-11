package com.secj3303.dao;

import java.util.List;
import com.secj3303.model.Module;

public interface ModuleDao {
    List<Module> findAll();
    void delete(Module module);
    Module findById(int id);
    void save(Module module);
}