package eu.europa.ec.digit.ecp.web.property.ws.rest.mapper;

import eu.europa.ec.digit.ecp.domain.property.Property;
import eu.europa.ec.digit.ecp.web.property.ws.rest.dto.PropertyHateosResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyResourceMapperImpl implements PropertyResourceMapper {

	/* ---- Constants ---- */

	/* ---- Instance Variables ---- */

	/* ---- Constructors ---- */

	/* ---- Business Methods ---- */
    @Override
    public Property mapDomain(Property property) {

        PropertyHateosResource resource = null;

        if (property != null) {
            resource = new PropertyHateosResource(property);
        }

        return resource;

    }

    @Override
    public List<Property> mapDomains(List<Property> domains) {

        List<Property> objects = new ArrayList<Property>();
        for (Property domain : domains) {
            objects.add(this.mapDomain(domain));
        }
        return objects;
    }

    /* ---- Getters and Setters ---- */
}
