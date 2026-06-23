package com.CodeVector.DemoTask.Service;

import com.CodeVector.DemoTask.Config.ProductSeeder;
import com.CodeVector.DemoTask.DTO.ProductResponseDTO;
import com.CodeVector.DemoTask.Enums.Category;
import com.CodeVector.DemoTask.Model.Products;
import com.CodeVector.DemoTask.Repo.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductSeeder seeder;
    @Autowired
    Repo repo;

    public String seed() throws SQLException, ClassNotFoundException {
        seeder.seedProducts();
        return "success";
    }

    public ProductResponseDTO findPage(Long cursor, int Pagesize, Category category) throws SQLException, ClassNotFoundException {
        List<Products>products=new ArrayList<Products>();
        if(category!=null){
            products=this.repo.findByCategory(cursor, Pagesize+1, category.name());
        }
        else{
            products=this.repo.findNextPage(cursor, Pagesize+1);
        }

        String nextPage=null;

        if (products.isEmpty()) {
            return new ProductResponseDTO(Collections.emptyList(), null, false, nextPage);
        }

        Boolean Hasmore = products.size()>Pagesize;
        Long curr=null;
        curr = products.get(products.size() - 1).getId();
        if(Hasmore){
            products.remove(products.size() - 1);
            nextPage = "/products?pageSize=" + Pagesize + "&cursor=" + curr;
            if(category!=null){
                nextPage = nextPage + "&category=" + category;
            }
        }

        ProductResponseDTO responseDTO = new ProductResponseDTO(products,curr,Hasmore,nextPage);
        return responseDTO;
    }
}
