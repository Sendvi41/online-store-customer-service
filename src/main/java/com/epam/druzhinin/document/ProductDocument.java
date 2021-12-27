package com.epam.druzhinin.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
@Document(indexName = "products")
public class ProductDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Keyword, name = "category")
    private String category;

    @Field(type = FieldType.Integer, name = "amount")
    private Integer amount;

    @Field(type = FieldType.Text, name = "price")
    private BigDecimal price;

    @Field(type = FieldType.Date, name = "date")
    private ZonedDateTime date;
}
