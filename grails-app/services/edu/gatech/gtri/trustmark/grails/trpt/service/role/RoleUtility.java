package edu.gatech.gtri.trustmark.grails.trpt.service.role;

import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;

public final class RoleUtility {

    private RoleUtility() {
    }

    public static RoleResponse roleResponse(final Role role) {

        return new RoleResponse(
                role.getValue(),
                role.getLabel());
    }
}
