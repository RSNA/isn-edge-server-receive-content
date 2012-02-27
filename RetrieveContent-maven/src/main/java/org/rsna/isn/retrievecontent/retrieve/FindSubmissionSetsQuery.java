/**
 * FindSubmissionSetsQuery.java 
 * Created on Nov 18, 2011 by damienevans 
 * Copyright 2011, itMD, LLC. All Rights Reserved.
 */
package org.rsna.isn.retrievecontent.retrieve;

import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQuery;
import org.openhealthtools.ihe.xds.metadata.AvailabilityStatusType;

/**
 * @author damienevans
 * 
 */
public class FindSubmissionSetsQuery extends StoredQuery implements
        StoredQueryConstantsExt {

    /**
     * Constructor, with minimal parameters.
     * 
     * @param patientID
     *            id of the patient, as a string, cannot be null
     * @param status
     *            document status list, cannot be null
     * @throws MalformedStoredQueryException
     */
    public FindSubmissionSetsQuery(CX patientID, AvailabilityStatusType[] status)
            throws MalformedStoredQueryException {
        super(FIND_SUBMISSION_SETS_UUID);
        if (patientID == null) {
            throw new MalformedStoredQueryException(
                    "Null patient ID. Cannot proceed with query.");
        }
        if (patientID.getIdNumber() == null) {
            throw new MalformedStoredQueryException(
                    "Empty patient ID number. Cannot proceed with query.");
        }
        if (patientID.getAssigningAuthorityUniversalId() == null) {
            throw new MalformedStoredQueryException(
                    "Empty patient ID assigning authority universal id. Cannot proceed with query.");
        }
        if (patientID.getAssigningAuthorityUniversalIdType() == null) {
            throw new MalformedStoredQueryException(
                    "Empty atient ID assigning authority universal id type. Cannot proceed with query.");
        }
        if (!patientID.getAssigningAuthorityUniversalIdType().equals("ISO")) {
            throw new MalformedStoredQueryException(
                    "Patient ID assigning authority universal id type is not \'ISO\'. Cannot proceed with query.");
        }
        if (status == null) {
            throw new MalformedStoredQueryException(
                    "Empty status. Cannot proceed with query.");
        }
        if (status[0] == null) {
            throw new MalformedStoredQueryException(
                    "Empty status. Cannot proceed with query.");
        }

        // add patient id parameter
        StoredQueryBuilderUtilsExt.addPatientIdParameter(this.queryParameters,
                patientID, false);

        // add status
        StoredQueryBuilderUtilsExt.addStatusParameters(this.queryParameters,
                status, false);
    }
}