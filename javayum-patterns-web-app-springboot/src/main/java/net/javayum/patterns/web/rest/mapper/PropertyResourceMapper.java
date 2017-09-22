package eu.europa.ec.digit.ecp.web.property.ws.rest.mapper;

import eu.europa.ec.digit.ecp.domain.property.Property;

import java.util.List;

public interface PropertyResourceMapper {
    /* ---- Business Methods ---- */
    Property mapDomain(Property property);

    List<Property> mapDomains(List<Property> domains);

    /* ---- Constants ---- */

	/* ---- Instance Variables ---- */

	/* ---- Constructors ---- */

	/* ---- Business Methods ---- */
}
