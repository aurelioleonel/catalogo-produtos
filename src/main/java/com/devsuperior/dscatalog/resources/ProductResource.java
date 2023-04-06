package com.devsuperior.dscatalog.resources;


import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;


//resource = controller

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    //Controller -> Service -> Repository -> Banco de dados
    //Dtos ficam entre os controller e o Service
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
        Page<ProductDto> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ProductDto>> findById(@PathVariable Long id) {
        ProductDto dto = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(dto));
    }

    @PostMapping
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);

//        @PostMapping
//        public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
//            clienteService.inserir(cliente);
//            return ResponseEntity.ok(cliente);
//        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();


    }

}


//List<Product> list = new ArrayList<>();
//list.add(new Product(1L, "Books"));
//list.add(new Product(2L, "Eletronics"));
