package io.vieira.controller;

import io.vieira.model.PutRequest;
import io.vieira.service.VieiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VieiraController {

    @Autowired private VieiraService vieiraService;

    @PostMapping("/update")
    public void update(@RequestBody PutRequest request) {
        vieiraService.put(request);
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable("key") String key) {
        return vieiraService.get(key);
    }

    @DeleteMapping("/remove/{key}")
    public void remove(@PathVariable("key") String key) {
        vieiraService.remove(key);
    }
}
