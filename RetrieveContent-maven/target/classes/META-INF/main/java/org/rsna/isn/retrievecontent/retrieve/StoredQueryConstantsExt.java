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

import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryConstants;

/**
 * StoredQueryConstantsExt.java to extend OHT org.openhealthtools.ihe.xds.consumer.storedquery.StoredQueryConstants
 * 
 * @version @author     Date         Note
 * 1.0.0    damienevans 11/2011     Created the original version
 *          wendyzhu    12/2011     Updated with submissionsets
 */
public interface StoredQueryConstantsExt extends StoredQueryConstants {
    public static final String FIND_SUBMISSION_SETS_UUID = "urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9";
    public static final String GET_SUBMISSION_SETS_UUID = "urn:uuid:51224314-5390-4169-9b91-b1980040715a";
    public static final String GET_SUBMISSION_SETS_AND_CONTENTS_UUID = "urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83";
    
    
    public static final String SS_PATIENT_ID = "$XDSSubmissionSetPatientId";
    public static final String SS_STATUS = "$XDSSubmissionSetStatus";
    // get documents
    public static final String SS_ENTRY_UUID = "$XDSSubmissionSetEntryUUID";
    public static final String SS_UNIQUE_ID = "$XDSSubmissionSetUniqueId";
    
//    public static final String SS_AUTHOR_PERSON = "$XDSSubmissionSetAuthorPerson";
//    public static final String SS_CLASS_CODE = "$XDSSubmissionSetClassCode";
//    public static final String SS_CLASS_CODE_SCHEME = "$XDSSubmissionSetClassCodeScheme";
//    public static final String SS_PRAC_SETTING_CODE = "$XDSSubmissionSetPracticeSettingCode";
//    public static final String SS_PRAC_SETTING_CODE_SCHEME = "$XDSSubmissionSetPracticeSettingCodeScheme";
//    public static final String SS_CREATION_TIME_FROM = "$XDSSubmissionSetCreationTimeFrom";
//    public static final String SS_CREATION_TIME_TO = "$XDSSubmissionSetCreationTimeTo";
//    public static final String SS_SERVICE_START_TIME_FROM = "$XDSSubmissionSetServiceStartTimeFrom";
//    public static final String SS_SERVICE_START_TIME_TO = "$XDSSubmissionSetServiceStartTimeTo";
//    public static final String SS_SERVICE_STOP_TIME_FROM = "$XDSSubmissionSetServiceStopTimeFrom";
//    public static final String SS_SERVICE_STOP_TIME_TO = "$XDSSubmissionSetServiceStopTimeTo";
//    public static final String SS_HC_FACILITY_CODE = "$XDSSubmissionSetHealthcareFacilityTypeCode";
//    public static final String SS_HC_FACILITY_CODE_SCHEME = "$XDSSubmissionSetHealthcareFacilityTypeCodeScheme";
//    public static final String SS_EVENT_CODE = "$XDSSubmissionSetEventCodeList";
//    public static final String SS_EVENT_CODE_SCHEME = "$XDSSubmissionSetEventCodeListScheme";
//    public static final String SS_CONF_CODE = "$XDSSubmissionSetConfidentialityCode";
//    public static final String SS_FORMAT_CODE = "$XDSSubmissionSetFormatCode";
//    public static final String SS_TYPE_CODE = "$XDSSubmissionSetTypeCode";
//    public static final String SS_TYPE_CODE_SCHEME = "$XDSSubmissionSetTypeCodeScheme";

}
