package com.pontshocodes.arejekota_backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pontshocodes.arejekota_backend.dto.MenuItemRequest;
import com.pontshocodes.arejekota_backend.dto.MenuItemResponse;
import com.pontshocodes.arejekota_backend.service.MenuService;

@RestController
@RequestMapping("/api/vendor")
public class MenuItemController {

    private final MenuService menuService;

    public MenuItemController(MenuService menuService){
        this.menuService=menuService;
    }

    @PostMapping("/add-item")
    public ResponseEntity<MenuItemResponse> addItem (@RequestHeader("X-work-Email")
    String workEmail , @ModelAttribute MenuItemRequest request ,@RequestParam("image") MultipartFile image){
        
        MenuItemResponse response = menuService.addItem(workEmail , request ,image);

        return ResponseEntity.ok(response);
        

    }

}