/*******************************************************************************
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 * 
 * This file is part of the Alfresco Mobile SDK.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.api.services.impl.publicapi;

import java.util.Map;

import org.alfresco.mobile.android.api.constants.PublicAPIConstant;
import org.alfresco.mobile.android.api.exceptions.ErrorCodeRegistry;
import org.alfresco.mobile.android.api.model.Node;
import org.alfresco.mobile.android.api.services.impl.AbstractRatingsService;
import org.alfresco.mobile.android.api.session.AlfrescoSession;
import org.alfresco.mobile.android.api.session.impl.RepositorySessionImpl;
import org.alfresco.mobile.android.api.utils.PublicAPIResponse;
import org.alfresco.mobile.android.api.utils.PublicAPIUrlRegistry;
import org.apache.chemistry.opencmis.client.bindings.spi.http.Response;
import org.apache.chemistry.opencmis.commons.impl.JSONConverter;
import org.apache.chemistry.opencmis.commons.impl.UrlBuilder;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The RatingsService can be used to manage like (as ratings) on any content
 * node in the repository.<br>
 * Like can be applied or removed.
 * 
 * @author Jean Marie Pascal
 */
public class PublicAPIRatingsServiceImpl extends AbstractRatingsService
{

    /**
     * Default Constructor. Only used inside ServiceRegistry.
     * 
     * @param repositorySession : Repository Session.
     */
    public PublicAPIRatingsServiceImpl(AlfrescoSession repositorySession)
    {
        super(repositorySession);
    }

    /** {@inheritDoc} */
    protected UrlBuilder getRatingsUrl(Node node)
    {
        return new UrlBuilder(PublicAPIUrlRegistry.getRatingsUrl(session, node.getIdentifier()));
    }

    /** {@inheritDoc} */
    protected JSONObject getRatingsObject()
    {
        JSONObject jo = new JSONObject();
        jo.put(PublicAPIConstant.MYRATING_VALUE, true);
        jo.put(PublicAPIConstant.ID_VALUE, PublicAPIConstant.LIKES_VALUE);
        return jo;
    }

    /** {@inheritDoc} */
    protected UrlBuilder getUnlikeUrl(Node node)
    {
        return new UrlBuilder(PublicAPIUrlRegistry.getUnlikeUrl(session, node.getIdentifier()));
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // / INTERNAL
    // ////////////////////////////////////////////////////////////////////////////////////
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    protected int computeRatingsCount(UrlBuilder url)
    {
        // read and parse
        Response resp = read(url, ErrorCodeRegistry.RATING_GENERIC);
        PublicAPIResponse response = new PublicAPIResponse(resp);

        Map<String, Object> data = null;
        for (Object entry : response.getEntries())
        {
            data = (Map<String, Object>) ((Map<String, Object>) entry).get(PublicAPIConstant.ENTRY_VALUE);
            if (data.containsKey(PublicAPIConstant.ID_VALUE)
                    && PublicAPIConstant.LIKES_VALUE.equals(data.get(PublicAPIConstant.ID_VALUE))
                    && data.containsKey(PublicAPIConstant.AGGREGATE_VALUE)) { return JSONConverter.getInteger(
                    (Map<String, Object>) data.get(PublicAPIConstant.AGGREGATE_VALUE), PublicAPIConstant.NUMBEROFRATINGS_VALUE)
                    .intValue();

            }
        }

        return -1;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    protected boolean computeIsRated(UrlBuilder url)
    {
        // read and parse
        Response resp = read(url, ErrorCodeRegistry.RATING_GENERIC);
        PublicAPIResponse response = new PublicAPIResponse(resp);

        Map<String, Object> data = null;
        for (Object entry : response.getEntries())
        {
            data = (Map<String, Object>) ((Map<String, Object>) entry).get(PublicAPIConstant.ENTRY_VALUE);
            if (data.containsKey(PublicAPIConstant.ID_VALUE)
                    && PublicAPIConstant.LIKES_VALUE.equals(data.get(PublicAPIConstant.ID_VALUE))
                    && data.containsKey(PublicAPIConstant.MYRATING_VALUE)) { return true; }
        }

        return false;
    }

    // ////////////////////////////////////////////////////
    // Save State - serialization / deserialization
    // ////////////////////////////////////////////////////
    public static final Parcelable.Creator<PublicAPIRatingsServiceImpl> CREATOR = new Parcelable.Creator<PublicAPIRatingsServiceImpl>()
    {
        public PublicAPIRatingsServiceImpl createFromParcel(Parcel in)
        {
            return new PublicAPIRatingsServiceImpl(in);
        }

        public PublicAPIRatingsServiceImpl[] newArray(int size)
        {
            return new PublicAPIRatingsServiceImpl[size];
        }
    };

    public PublicAPIRatingsServiceImpl(Parcel o)
    {
        super((AlfrescoSession) o.readParcelable(RepositorySessionImpl.class.getClassLoader()));
    }

}
