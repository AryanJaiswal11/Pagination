package com.CodeVector.DemoTask.Repo;

import com.CodeVector.DemoTask.Enums.Category;
import com.CodeVector.DemoTask.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Repo extends JpaRepository<Products,Long> {

    //cursor pagination
    @Query(value=""" 
              SELECT * FROM products 
              WHERE (:cursor IS NULL OR id< :cursor)
              ORDER BY (updated_at, id) DESC
              LIMIT :limit""",
    nativeQuery=true)
    public List<Products>findNextPage(@Param("cursor") Long cursor, @Param("limit") int limit);

    @Query(value = """ 
              SELECT * FROM products 
              WHERE (category = CAST(:category AS varchar))
                AND (:cursor IS NULL OR id < :cursor)
              ORDER BY id DESC
              LIMIT :fetchLimit""",
            nativeQuery = true)
    List<Products> findByCategory(
            @Param("cursor") Long cursor,
            @Param("fetchLimit") int fetchLimit,
            @Param("category") String category);
}
