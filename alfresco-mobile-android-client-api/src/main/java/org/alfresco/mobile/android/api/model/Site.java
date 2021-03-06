/*******************************************************************************
 * Copyright (C) 2005-2013 Alfresco Software Limited.
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
package org.alfresco.mobile.android.api.model;

import java.io.Serializable;

/**
 * Provides informations about Alfresco Share site. </br> A site is a project
 * area where you can share content and collaborate with other site
 * members.</br> Each site has a visibility setting that marks the site as
 * public or private.
 * 
 * @author Jean Marie Pascal
 */
public interface Site extends Serializable
{

    /**
     * Returns the unique identifier of the site.
     * 
     * @since 1.1.0
     * @return the identifier
     */
    String getIdentifier();

    /**
     * Returns a globally unique identifier for the site.
     * 
     * @since 1.1.0
     * @return GUID of the site
     */
    String getGUID();

    /**
     * Returns the short name of the site.
     * 
     * @return the short name
     */
    String getShortName();

    /**
     * Returns the unique identifier of the site.
     * 
     * @return the title
     */
    String getTitle();

    /**
     * Returns the description of the site.
     * 
     * @return the description
     */
    String getDescription();

    /**
     * Returns the visibility of the site i.e. “public”, “moderated” or
     * “private”, represented by an enum.
     * 
     * @return the visibility
     */
    SiteVisibility getVisibility();

    /**
     * @since 1.1.0
     * @return true if the current user is a member of the site.
     */
    boolean isMember();

    /**
     * @since 1.1.0
     * @return true if the current user has requested to join the site. If the
     *         site is not moderated this will return false.
     */
    boolean isPendingMember();

    /**
     * @since 1.1.0
     * @return true if the current user has favorited the site.
     */
    boolean isFavorite();

}
