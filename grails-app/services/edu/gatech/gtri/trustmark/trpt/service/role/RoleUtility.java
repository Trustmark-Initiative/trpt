package edu.gatech.gtri.trustmark.trpt.service.role;

import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.RoleName;

public final class RoleUtility {

    private RoleUtility() {
    }

    public static RoleResponse roleResponse(final Role role) {

        return new RoleResponse(
                role.getIdHelper(),
                RoleName.valueOf(role.getNameHelper()).name(),
                RoleName.valueOf(role.getNameHelper()).getName());
    }
}
