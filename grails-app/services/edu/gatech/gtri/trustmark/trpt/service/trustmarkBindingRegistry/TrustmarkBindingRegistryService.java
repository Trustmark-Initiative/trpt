package edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry;

import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.Unit;

import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationDescription;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationId;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationIdList;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationName;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationOrganization;
import static edu.gatech.gtri.trustmark.trpt.service.trustmarkBindingRegistry.TrustmarkBindingRegistryUtility.validationUri;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.product.Unit.unit;

@Transactional
public class TrustmarkBindingRegistryService {

    public List<TrustmarkBindingRegistryResponse> findAll(final TrustmarkBindingRegistryFindAllRequest trustmarkBindingRegistryFindAllRequest) {

        return TrustmarkBindingRegistry
                .findAllByOrderByNameAscHelper()
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> findOne(final TrustmarkBindingRegistryFindOneRequest trustmarkBindingRegistryFindOneRequest) {

        return validationId(trustmarkBindingRegistryFindOneRequest.getId())
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> insert(final TrustmarkBindingRegistryInsertRequest trustmarkBindingRegistryInsertRequest) {

        return accumulate(
                validationOrganization(trustmarkBindingRegistryInsertRequest.getOrganization()),
                validationName(trustmarkBindingRegistryInsertRequest.getOrganization(), trustmarkBindingRegistryInsertRequest.getName()),
                validationUri(trustmarkBindingRegistryInsertRequest.getOrganization(), trustmarkBindingRegistryInsertRequest.getUri()),
                validationDescription(trustmarkBindingRegistryInsertRequest.getDescription()),
                (organization, name, uri, description) -> {

                    final TrustmarkBindingRegistry trustmarkBindingRegistry = new TrustmarkBindingRegistry();

                    trustmarkBindingRegistry.setName(name);
                    trustmarkBindingRegistry.setDescription(description);
                    trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper(upsert(uri));
                    trustmarkBindingRegistry.organizationHelper(organization);
                    trustmarkBindingRegistry.saveAndFlushHelper();

                    return trustmarkBindingRegistry;
                })
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, TrustmarkBindingRegistryResponse> update(final TrustmarkBindingRegistryUpdateRequest trustmarkBindingRegistryUpdateRequest) {

        return accumulate(
                validationId(trustmarkBindingRegistryUpdateRequest.getId()),
                validationOrganization(trustmarkBindingRegistryUpdateRequest.getOrganization()),
                validationName(trustmarkBindingRegistryUpdateRequest.getId(), trustmarkBindingRegistryUpdateRequest.getOrganization(), trustmarkBindingRegistryUpdateRequest.getName()),
                validationUri(trustmarkBindingRegistryUpdateRequest.getId(), trustmarkBindingRegistryUpdateRequest.getOrganization(), trustmarkBindingRegistryUpdateRequest.getUri()),
                validationDescription(trustmarkBindingRegistryUpdateRequest.getDescription()),
                (trustmarkBindingRegistry, organization, name, uri, description) -> {

                    trustmarkBindingRegistry.setName(name);
                    trustmarkBindingRegistry.setDescription(description);
                    trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper(upsert(uri));
                    trustmarkBindingRegistry.organizationHelper(organization);
                    trustmarkBindingRegistry.saveAndFlushHelper();

                    return trustmarkBindingRegistry;
                })
                .map(TrustmarkBindingRegistryUtility::trustmarkBindingRegistryResponse);
    }

    public Validation<NonEmptyList<ValidationMessage<TrustmarkBindingRegistryField>>, Unit> delete(final TrustmarkBindingRegistryDeleteAllRequest trustmarkBindingRegistryDeleteAllRequest) {

        return validationIdList(iterableList(trustmarkBindingRegistryDeleteAllRequest.getIdList()))
                .map(list -> list.map(trustmarkBindingRegistry -> {
                    trustmarkBindingRegistry.deleteAndFlushHelper();

                    return trustmarkBindingRegistry.idHelper();
                }))
                .map(ignore -> unit());
    }

    private TrustmarkBindingRegistryUri upsert(final String uri) {

        return TrustmarkBindingRegistryUri.findByUriHelper(uri).orSome(() -> {

            final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri = new TrustmarkBindingRegistryUri();
            trustmarkBindingRegistryUri.setUri(uri);

            return trustmarkBindingRegistryUri.saveHelper();
        });
    }
}
