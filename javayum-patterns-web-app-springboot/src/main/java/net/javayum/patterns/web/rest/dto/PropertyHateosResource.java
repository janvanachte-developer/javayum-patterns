package eu.europa.ec.digit.ecp.web.property.ws.rest.dto;

import eu.europa.ec.digit.ecp.domain.property.Property;
import eu.europa.ec.digit.ecp.web.property.ws.rest.DatabaseBackedPropertyWebServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PropertyHateosResource extends ResourceSupport implements Property {

	/* ---- Constants ---- */

	/* ---- Instance Variables ---- */
    private String key;
    private String value;

	/* ---- Constructors ---- */
    public PropertyHateosResource(Property property) {
        key = property.getKey();
        value = property.getValue();

        Link selfLink = linkTo(DatabaseBackedPropertyWebServiceImpl.class).slash(getKey()).withSelfRel();
        add(selfLink);
    }

	/* ---- Business Methods ---- */
	@Override
    public String toString() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return super.toString();
        }
    }

    /* ---- Getters and Setters ---- */
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
