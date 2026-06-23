package com.CodeVector.DemoTask.DTO;

import com.CodeVector.DemoTask.Model.Products;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponseDTO {
    private List<Products>products;
    private Long cursor;
    private Boolean HasMore;
    private String nextPage;

    public ProductResponseDTO(List<Products> products, Long cursor, Boolean HasMore,String nextPage) {
        this.products = products;
        this.cursor = cursor;
        this.HasMore = HasMore;
        this.nextPage = nextPage;
    }
}
