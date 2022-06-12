package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Product;
import com.diamondjewelry.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/products", consumes = MediaType.ALL_VALUE)
public class ProductController {
    @Autowired
    private ProductService service;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "material", required = false) String material,
            @RequestParam(name = "chainMaterial", required = false) String chainMaterial,
            @RequestParam(name = "purity", required = false) String purity,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "group", required = false) String group,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "sortMode", required = false, defaultValue = "latest") String sortMode,
            @RequestParam(name = "limit", required = false, defaultValue = "0") int limit) {
        Map<String, String> params = new HashMap<>();
        params.put("brand", brand);
        params.put("material", material);
        params.put("chain_material", chainMaterial);
        params.put("purity", purity);
        params.put("gender", gender);
        params.put("color", color);
        params.put("group", group);
        params.put("type", type);
        if (!sortMode.equals("oldest") && !sortMode.equals("latest") && !sortMode.equals("mostPopular")) {
            return new ResponseEntity<>("Invalid sorting mode", HttpStatus.BAD_REQUEST);
        }
        if (limit < 0) {
            return new ResponseEntity<>("Invalid limit: less than 0", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.getProduct(params, sortMode, limit), HttpStatus.OK);
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

    @RequestMapping(method = RequestMethod.GET, value = "/filter/{filter}")
    public ResponseEntity<?> getAllValuesOfFilter(@PathVariable("filter") String filter) {
        if (!filter.equals("brand") && !filter.equals("material") && !filter.equals("chain_material")
            && !filter.equals("purity") && !filter.equals("gender") && !filter.equals("color") && !filter.equals("type")) {
            return new ResponseEntity<>("Invalid filter", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.getAllValuesOfFilter(filter), HttpStatus.OK);
    }
}
