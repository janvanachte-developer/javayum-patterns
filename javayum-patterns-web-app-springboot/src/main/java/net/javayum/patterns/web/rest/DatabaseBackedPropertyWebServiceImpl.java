package eu.europa.ec.digit.ecp.web.property.ws.rest;

import eu.europa.ec.digit.ecp.domain.property.Property;
import eu.europa.ec.digit.ecp.domain.property.dto.PropertyDTO;
import eu.europa.ec.digit.ecp.facade.property.facade.DatabaseBackedPropertyFacade;
import eu.europa.ec.digit.ecp.web.property.ws.rest.mapper.PropertyResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class DatabaseBackedPropertyWebServiceImpl {

	/* ---- Constants ---- */
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_HTML = "text/html";

	/* ---- Instance Variables ---- */
	@Autowired
    private DatabaseBackedPropertyFacade facade;

	@Autowired
    private PropertyResourceMapper jsonMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

	/* ---- Constructors ---- */

	/* ---- Business Methods ---- */
    @RequestMapping(value = "/index.html", method = RequestMethod.GET, produces = TEXT_HTML)
    public void testPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("<html><head></head><body>Test page of Database Backed Property REST service. <a href=\"https://en.wikipedia.org/wiki/HATEOAS\">HATEOAS<a> list of Database Backed Properties is available in <a href=\"./\">JSON format<a>.</body></html>");
        } catch (IOException ioe) {
            logger.error("Unable to test page content", ioe);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public List<Property> findAll() {

        return jsonMapper.mapDomains(facade.findAll());
    }

    /**
     * This updates also the Spring Environment
     */
    @RequestMapping(path = "/{key:.+}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    // ":.+ added because of Spring bug truncating PathVariable with dots. See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    public Property findOneInDbAndUpdateEnvironment(@PathVariable String key) { return jsonMapper.mapDomain(facade.findOneInDbAndUpdateEnvironment(key)); }

    @RequestMapping(path = "/{key:.+}", method = RequestMethod.PUT)
    // ":.+ added because of Spring bug truncating PathVariable with dots. See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    public ResponseEntity<?> update(@PathVariable String key, @RequestBody PropertyDTO property) {

        if (property != null && property.getKey() != null && property.getValue() != null && property.getKey().equals(key)) {

            facade.saveInDbAndProduceEventToSignalAllNodesToUpdateEnvironment(property);

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    /* ---- Getters and Setters ---- */
}
