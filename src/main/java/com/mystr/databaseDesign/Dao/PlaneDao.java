package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneDao extends JpaRepository<Plane,Integer>{
    List<Plane> findAll();

    Plane findByPlaneId(int planeId);
    List<Plane> findAllByType(String type);
    List<Plane> findAllByState(int state);

    boolean existsByPlaneId(int planeId);

    boolean existsByPlaneNum(String planeNum);


    @Query(value = "select * from plane where plane_num like '%?1%'", nativeQuery = true)
    List<Plane> SearchByPlaneNum(String planeNum);

    @Query(value = "select * from plane where x_capacity >= ?1 and x_capacity <= ?2", nativeQuery = true)
    List<Plane> findByXCapacityBetween(int XCapacityStart, int XCapacityEnd);

    @Query(value = "select * from plane where y_capacity >= ?1 and y_capacity <= ?2", nativeQuery = true)
    List<Plane> findByYCapacityBetween(int YCapacityStart, int YCapacityEnd);

    @Query(value = "select * from plane where x_capacity + y_capacity >= ?1 and x_capacity + y_capacity <= ?2", nativeQuery = true)
    List<Plane> findByCapacityBetween(int CapacityStart, int CapacityEnd);

    //@Query("select p from Plane p where upper(p.type) like '%upper(?1)%'")
    //List<Plane> findByTypeLikeIgnoreCase(String type);

    List<Plane> findByPlaneNumContainsIgnoreCase(String planeNum);

    List<Plane> findByTypeContainsIgnoreCase(String type);




    void deleteByPlaneId(int planeId);
    void deleteByState(int state);


}

