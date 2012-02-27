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

import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.consumer.storedquery.StoredQuery;
/**
 * GetSubmissionSetsQuery
 * argument is configuration key
 * <p>
 * @param  String key
 * @return  String value
 *
 * @version @author    Date         Note
 * 1.0.0    Wendy Zhu  12/5/2011    Created the original version
 *
 */
public class GetSubmissionSetAndContentsQuery extends StoredQuery implements
        StoredQueryConstantsExt {

    
	/**
	 * Constructor.
	 * @param docID id of the submission set (either uniqueId or entryUUID)
	 * @param isUUID set to true if docID is the entryUUID (internal registry identifier) of the
	 * document and set to false if it is the uniqueID (external to registry) of the document. In most
	 * user cases, this should be set to false
	 * @throws MalformedQueryException 
	 */
	public GetSubmissionSetAndContentsQuery(String docID, boolean isUUID) throws MalformedStoredQueryException{
		super(GET_SUBMISSION_SETS_AND_CONTENTS_UUID);
		if(docID == null){
			throw new MalformedStoredQueryException("Null submission set ID. Cannot proceed with query.");
		}
		
		if(isUUID){
			this.queryParameters.put(StoredQueryConstantsExt.SS_ENTRY_UUID, docID);
		}
		else{
			this.queryParameters.put(StoredQueryConstantsExt.SS_UNIQUE_ID, docID);
		}
	}
	
	/**
	 * Constructor that allows for the addition of a homeCommunityId to the query to support the
	 * XCA profile extension of this query. 
	 * @param docID id of the submission set (either uniqueId or entryUUID)
	 * @param isUUID set to true if docID is the entryUUID (internal registry identifier) of the
	 * document and set to false if it is the uniqueID (external to registry) of the document. In most
	 * user cases, this should be set to false
	 * @param homeCommunityId this is the id of the home community as specified by the XCA profile. Value 
	 * may be null or empty, in which case it is not added to the query.
	 * @throws MalformedQueryException 
	 * @see http://www.ihe.net/Technical_Framework/index.cfm#IT for more information on XCA
	 */
	public GetSubmissionSetAndContentsQuery(String docID, boolean isUUID, String homeCommunityId) throws MalformedStoredQueryException{
		this(docID, isUUID);
		if(homeCommunityId != null){
			if(homeCommunityId.length() > 0){
				this.homeCommunityId = homeCommunityId;
			}
		}	
	}
}
