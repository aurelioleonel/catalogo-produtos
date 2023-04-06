package com.devsuperior.dscatalog.resources;


import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;


//resource = controller

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    //Controller -> Service -> Repository -> Banco de dados
    //Dtos ficam entre os controller e o Service
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> findAll(Pageable pageable) {
        Page<CategoryDto> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<CategoryDto>> findById(@PathVariable Long id) {
        CategoryDto dto = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(dto));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto dto) {
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
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();


    }

}


//List<Category> list = new ArrayList<>();
//list.add(new Category(1L, "Books"));
//list.add(new Category(2L, "Eletronics"));
