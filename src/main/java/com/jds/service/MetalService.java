package com.jds.service;

import com.jds.dao.repository.MetalRepository;
import com.jds.dao.entity.Metal;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetalService {

    @Autowired
    private MetalRepository dAO;

    public List<Metal> getMetals() {
        return dAO.getMetals();
    }

    public Metal getMetal(@NonNull String id) {

        if("0".equals(id)){
            return new Metal();
        }

        return dAO.getMetalById(Integer.parseInt(id));
    }

    public String saveMetal(@NonNull Metal metal) {
        dAO.saveMetal(metal);
        return "ok";
    }

    public String deleteMetal(@NonNull String id) {
        Metal metal = getMetal(id);
        return dAO.deleteMetal(metal);
    }
}
