package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.AirlineDao;
import com.mystr.databaseDesign.Dao.PlaneDao;
import com.mystr.databaseDesign.Entities.Plane;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.utils.State;
import com.mystr.databaseDesign.utils.StringFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class PlaneService {
    @Autowired
    PlaneDao planeDao;
    @Autowired
    AirlineDao airlineDao;

    public List<Plane> getAll() {
        return planeDao.findAll();
    }
    public Plane getByPlaneId(int planeId) {
        return planeDao.findByPlaneId(planeId);
    }
    public List<Plane> getByType(String type){ return planeDao.findAllByType(type); }
    public List<Plane> getByState(int state) { return planeDao.findAllByState(state); }
    public List<Plane> getByXCapacity(int start,int end) {return planeDao.findByXCapacityBetween(start,end);}
    public List<Plane> getByYCapacity(int start,int end) {return planeDao.findByYCapacityBetween(start,end);}
    public List<Plane> getByCapacity(int start,int end) {return planeDao.findByCapacityBetween(start,end);}

    public List<Plane> search(String keyword) {
        String[] keywords = StringFactory.StringSplitterSpace(keyword);
        List<Plane> resultA = new ArrayList<Plane>();
        List<Plane> resultB = new ArrayList<Plane>();
        List<Plane> temp;
        boolean useNum = false;
        boolean useType = false;
        for(String s : keywords){
            if(s.startsWith("#")){
                temp = planeDao.findByPlaneNumContainsIgnoreCase(s.substring(1));
                resultA.removeAll(temp);
                resultA.addAll(temp);
                useNum = true;
            }
            if(s.startsWith("T/")){
                temp = planeDao.findByTypeContainsIgnoreCase(s.substring(2));
                resultB.removeAll(temp);
                resultB.addAll(temp);
                useType = true;
            }
        }
        if(!useNum)
            return resultB;
        if(!useType)
            return resultA;
        resultA.retainAll(resultB);
        return resultA;
    }

    public void deleteByPlaneId(int planeId) throws Exception{
        if(deleteChecker(planeId))
            planeDao.deleteByPlaneId(planeId);
    }
    public void deleteDeserted() { planeDao.deleteByState(State.PLANE_DESERTED.code);}


    public void addOrUpdate(Plane plane) throws Exception{
        if(planeDao.existsByPlaneId(plane.getPlaneId())) //修改
            planeDao.save(plane);
        else {  //添加
            if(addChecker(plane))
                planeDao.save(plane);
            else
                throw new Exception();
        }
    }
    private boolean addChecker(Plane plane){
        if(planeDao.existsByPlaneNum(plane.getPlaneNum()))
            return false;
        return true;
    }

    private boolean deleteChecker(int plane_id) throws Exception{
        if(airlineDao.existsByPlane_PlaneId(plane_id))
            throw new IllegalAccessException();
        return true;
    }

}

