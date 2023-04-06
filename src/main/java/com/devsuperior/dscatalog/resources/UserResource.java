package com.devsuperior.dscatalog.resources;


import com.devsuperior.dscatalog.dto.UserDto;
import com.devsuperior.dscatalog.dto.UserInsertDto;
import com.devsuperior.dscatalog.dto.UserUpdateDto;
import com.devsuperior.dscatalog.services.UserService;
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
@RequestMapping(value = "/users")
public class UserResource {
//Controller -> Service -> Repository -> Banco de dados
//Dtos ficam entre os controller e o Service

    //    @RequestParam(value = "page", defaultValue = "0") Integer page,
//    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
//    @RequestParam(value = "direction", defaultValue = "ASC") String direction,
//    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
//    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserDto>> findById(@PathVariable Long id) {
        UserDto dto = service.findById(id);
        return ResponseEntity.ok().body(Collections.singletonList(dto));
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@Valid @RequestBody UserInsertDto dto) {
        UserDto newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newDto);

//        @PostMapping
//        public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
//            clienteService.inserir(cliente);
//            return ResponseEntity.ok(cliente);
//        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        UserDto newDto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();


    }

}


//List<User> list = new ArrayList<>();
//list.add(new User(1L, "Books"));
//list.add(new User(2L, "Eletronics"));
