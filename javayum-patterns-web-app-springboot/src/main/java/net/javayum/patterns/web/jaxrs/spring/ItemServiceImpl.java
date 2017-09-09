package net.javayum.patterns.web.jaxrs.spring;

import java.util.concurrent.atomic.AtomicLong;

import net.javayum.patterns.domain.ItemDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemServiceImpl {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/item")
    public ItemDTO item(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new ItemDTO(counter.incrementAndGet(),
                String.format(template, name));
    }
}

