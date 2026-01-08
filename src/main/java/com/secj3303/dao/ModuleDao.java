package com.secj3303.dao;

import java.util.List;
import com.secj3303.model.Module;

public interface ModuleDao {
    List<Module> findAll();
    Module findByCategory(String category);
    Module findById(int id);
    void save(Module module);
}