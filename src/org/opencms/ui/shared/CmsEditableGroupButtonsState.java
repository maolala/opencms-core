/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH & Co. KG (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.ui.shared;

import com.vaadin.shared.AbstractComponentState;

/**
 * State class for the CmsEditableGroupButtons component.<p>
 */
public class CmsEditableGroupButtonsState extends AbstractComponentState {

    /** Serial version id. */
    private static final long serialVersionUID = 1L;

    /** True if this is the button bar of the first row. */
    protected boolean m_isFirst;

    /** True if this is the button bar of the last row. */
    protected boolean m_isLast;

    /**
     * Default constructor.<p>
     */
    public CmsEditableGroupButtonsState() {
        // do nothing
    }

    /**
     * Returns the isFirst.<p>
     *
     * @return the isFirst
     */
    public boolean isFirst() {

        return m_isFirst;
    }

    /**
     * Returns the isLast.<p>
     *
     * @return the isLast
     */
    public boolean isLast() {

        return m_isLast;
    }

    /**
     * Sets the 'first' status (i.e. if this belongs to the first row.<p>
     *
     * @param isFirst true if the button bar belongs to the first row
     */
    public void setFirst(boolean isFirst) {

        m_isFirst = isFirst;

    }

    /**
     * Sets the 'last' status (i.e. if this belongs to the last row.<p>
     *
     * @param isLast true if the button bar belongs to the last row
     */
    public void setLast(boolean isLast) {

        m_isLast = isLast;

    }

}
