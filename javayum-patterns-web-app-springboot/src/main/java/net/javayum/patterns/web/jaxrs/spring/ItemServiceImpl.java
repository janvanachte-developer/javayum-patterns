package net.javayum.patterns.web.jaxrs.spring;

import java.util.concurrent.atomic.AtomicLong;

import net.javayum.patterns.domain.ItemDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemServiceImpl {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // localhost:8080/item?name=World
    @RequestMapping(method = RequestMethod.GET)
    public ItemDTO getByNameWithRequestParam(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new ItemDTO(counter.incrementAndGet(),
                String.format(template, name));
    }

    // localhost:8080/item/World
    @RequestMapping(path="/{name}", method = RequestMethod.GET)
    public ItemDTO getByName(@PathVariable String name) {
        return new ItemDTO(counter.incrementAndGet(),
                String.format(template, name));
    }

}

