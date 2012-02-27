/* Copyright (c) <2011>, <Radiological Society of North America>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <RSNA> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package org.rsna.isn.retrievecontent.retrieve;

import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.common.hl7v2.format.HL7V2MessageFormat;
import org.openhealthtools.ihe.common.hl7v2.format.MessageDelimiters;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryBuilderUtils;
import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryConstants;
import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryParameterList;
import org.openhealthtools.ihe.xds.metadata.AvailabilityStatusType;

/**
 * StoredQueryConstantsExt.java to extend OHT org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryConstants
 *
 * @version @author     Date         Note
 * 1.0.0    damienevans 11/2011     Created the original version
 *          wendyzhu    12/2011
 */
public class StoredQueryBuilderUtilsExt extends StoredQueryBuilderUtils {

    /**
     * Adds the patientId list given as query parameters for the metadata
     * attribute to the given HashMap of StoredQuery parameters.
     * 
     * @param queryParameters
     *            HashMap of stored query parameters, non null
     * @param patientId
     *            patient id to add to the query parameters, non null
     * @param documentLevel
     *            Boolean. If true, use the patient id as a
     *            XDSDocumentEntryPatientId in the query, otherwise it will be a
     *            XDSSubmissionSetPatientId
     */
    public static void addPatientIdParameter(
            StoredQueryParameterList queryParameters, CX patientId,
            boolean documentLevel) throws MalformedStoredQueryException {
        String clause = '\'' + HL7V2MessageFormat.toMessageString(patientId,
                MessageDelimiters.COMPONENT, MessageDelimiters.SUBCOMPONENT) + '\'';
        queryParameters.put(documentLevel ? StoredQueryConstants.DE_PATIENT_ID
                : StoredQueryConstantsExt.SS_PATIENT_ID, clause);
    }

    /**
     * Adds the status list given as query parameters for the metadata attribute
     * to the given List of StoredQuery parameters.
     * 
     * @param queryParameters
     *            List of stored query parameters, non null
     * @param status
     *            list of status to add to the query parameters, non null
     * @param documentLevel
     *            Boolean. If true, use the patient id as a
     *            XDSDocumentEntryPatientId in the query, otherwise it will be a
     *            XDSSubmissionSetPatientId
     */
    public static void addStatusParameters(
            StoredQueryParameterList queryParameters,
            AvailabilityStatusType[] status, boolean documentLevel)
            throws MalformedStoredQueryException {
        String[] statusString = new String[status.length];
        for (int i = 0; i < status.length; i++) {
            statusString[i] = '\''
                    + StoredQueryConstants.EBXML_3_0STATUS_PREFIX
                    + status[i].getName() + '\'';
        }
        String clause = '(' + StoredQueryBuilderUtils
                .buildListArgs(statusString) + ')';
        queryParameters.put(documentLevel ? StoredQueryConstants.DE_STATUS
                : StoredQueryConstantsExt.SS_STATUS, clause);
    }
}
