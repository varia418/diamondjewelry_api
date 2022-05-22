package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Product;
import com.diamondjewelry.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {
        if (!service.getProductById(id).isPresent()) {
            return new ResponseEntity<>("Product is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getProductById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchTitle")
    public ResponseEntity<List<Product>> getProductBySearchTitleKeyword(@RequestParam("keyword") String titleKeyword) {
        return new ResponseEntity<>(service.getProductBySearchTitleKeyword(titleKeyword), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/details")
    public ResponseEntity<List<Product>> getProduct(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "material", required = false) String material,
            @RequestParam(name = "chainMaterial", required = false) String chainMaterial,
            @RequestParam(name = "ageOfGold", required = false) String ageOfGold,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "majorType", required = false) String majorType,
            @RequestParam(name = "minorType", required = false) String minorType) {
        Map<String, String> params = new HashMap<>();
        params.put("brand", brand);
        params.put("material", material);
        params.put("chain_material", chainMaterial);
        params.put("age_of_gold", ageOfGold);
        params.put("gender", gender);
        params.put("color", color);
        params.put("major_type", majorType);
        params.put("minor_type", minorType);
        return new ResponseEntity<>(service.getProduct(params), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        service.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        if (!service.getProductById(product.getId()).isPresent()) {
            service.addProduct(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        service.updateProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") String id) {
        Optional<Product> product = service.getProductById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>("Product is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeProductById(id);
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/majorType")
    public ResponseEntity<List<String>> getAllMajorTypes() {
        return new ResponseEntity<>(service.getAllMajorTypes(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/minorType")
    public ResponseEntity<List<String>> getAllMinorTypes() {
        return new ResponseEntity<>(service.getAllMinorTypes(), HttpStatus.OK);
    }
}
