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

package org.opencms.ui.components.fileselect;

import org.opencms.file.CmsObject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.ui.A_CmsUI;
import org.opencms.ui.CmsVaadinUtils;
import org.opencms.ui.FontOpenCms;
import org.opencms.ui.components.CmsBasicDialog;
import org.opencms.ui.components.CmsErrorDialog;
import org.opencms.ui.components.OpenCmsTheme;
import org.opencms.ui.components.editablegroup.I_CmsEditableGroup;
import org.opencms.ui.components.fileselect.CmsResourceSelectDialog.Options;

import org.apache.commons.logging.Log;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 * Abstract file select field. Used by {@link org.opencms.ui.components.fileselect.CmsPathSelectField}.<p>
 *
 * @param <T> the value type
 */
public abstract class A_CmsFileSelectField<T> extends CustomField<T> implements I_CmsEditableGroup.I_HasError {

    /** Logger instance for this class. */
    private static final Log LOG = CmsLog.getLog(A_CmsFileSelectField.class);

    /** The serial version id. */
    private static final long serialVersionUID = 1L;

    /** The file select dialog caption. */
    protected String m_fileSelectCaption;

    /** The filter used for reading resources. */
    protected CmsResourceFilter m_filter;

    /** The start with sitemap view flag. */
    protected boolean m_startWithSitemapView;

    /** The text field containing the selected path. */
    protected TextField m_textField;

    /**CmsObject instance, doesn't have to be set. In normal case this is null.*/
    protected CmsObject m_cms;

    /**Indicates if changing the website should be possible. */
    protected boolean m_diableSiteSwitch;

    /**
     * Creates a new instance.<p>
     */
    public A_CmsFileSelectField() {
        m_textField = new TextField();
        m_textField.setWidth("100%");
        m_filter = CmsResourceFilter.ONLY_VISIBLE_NO_DELETED;
    }

    /**
     * Disables the site switch function.<p>
     */
    public void disableSiteSwitch() {

        m_diableSiteSwitch = true;
    }

    /**
     * @see org.opencms.ui.components.editablegroup.I_CmsEditableGroup.I_HasError#hasEditableGroupError()
     */
    public boolean hasEditableGroupError() {

        return m_textField.getComponentError() != null;
    }

    /**
     * Method to set cms object to make it possible to user other site context.<p>
     *
     * @param cms Object to use
     */
    public void setCmsObject(CmsObject cms) {

        m_cms = cms;
    }

    /**
     * Sets the caption of the file select dialog.<p>
     *
     * @param caption the caption
     */
    public void setFileSelectCaption(String caption) {

        m_fileSelectCaption = caption;
    }

    /**
     * Sets the filter to use for reading resources.<p>
     *
     * @param filter the new filter
     */
    public void setResourceFilter(CmsResourceFilter filter) {

        m_filter = filter;

    }

    /**
     * Sets the start with sitemap view flag.<p>
     *
     * @param startWithSitemapView the start with sitemap view flag
     */
    public void setStartWithSitempaView(boolean startWithSitemapView) {

        m_startWithSitemapView = startWithSitemapView;
    }

    /**
     * Gets the options object.<p>
     *
     * @return Options
     */
    protected Options getOptions() {

        Options options = new Options();
        return options;
    }

    /**
     * @see com.vaadin.ui.CustomField#initContent()
     */
    @Override
    protected CssLayout initContent() {

        CssLayout layout = new CssLayout();
        layout.addStyleName("o-fileselect");
        layout.setWidth("100%");
        // layout.setSpacing(true);
        layout.addComponent(m_textField);
        Label spacer = new Label("");
        spacer.addStyleName("o-fileselect-spacer");
        spacer.setContentMode(ContentMode.HTML);
        spacer.setValue("<div></div>");
        layout.addComponent(spacer);
        Button fileSelectButton = new Button("");
        fileSelectButton.addStyleName(OpenCmsTheme.BUTTON_ICON);
        fileSelectButton.setIcon(FontOpenCms.GALLERY);
        fileSelectButton.addStyleName("o-fileselect-button");
        layout.addComponent(fileSelectButton);

        fileSelectButton.addClickListener(new ClickListener() {

            /** Serial version id. */
            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {

                openFileSelector();
            }
        });
        return layout;
    }

    /**
     * Opens the file selector dialog.<p>
     */
    protected void openFileSelector() {

        try {

            final Window window = CmsBasicDialog.prepareWindow();
            window.setCaption(
                m_fileSelectCaption != null
                ? m_fileSelectCaption
                : CmsVaadinUtils.getMessageText(org.opencms.ui.components.Messages.GUI_FILE_SELECT_CAPTION_0));
            A_CmsUI.get().addWindow(window);
            CmsResourceSelectDialog fileSelect;

            //Switch if cms object was set.
            if (m_cms == null) {
                fileSelect = new CmsResourceSelectDialog(m_filter, A_CmsUI.getCmsObject(), getOptions());
            } else {
                fileSelect = new CmsResourceSelectDialog(m_filter, m_cms, getOptions());
                if (m_diableSiteSwitch) {
                    fileSelect.disableSiteSwitch();
                }
            }
            fileSelect.showSitemapView(m_startWithSitemapView);

            T value = getValue();
            if (value instanceof CmsResource) {
                fileSelect.showStartResource((CmsResource)value);
            } else if (value instanceof String) {
                fileSelect.openPath((String)value);
            }

            window.setContent(fileSelect);
            fileSelect.addSelectionHandler(new I_CmsSelectionHandler<CmsResource>() {

                public void onSelection(CmsResource selected) {

                    setResourceValue(selected);
                    window.close();
                }
            });
        } catch (CmsException e) {
            LOG.error(e.getLocalizedMessage(), e);
            CmsErrorDialog.showErrorDialog(e);
        }
    }

    /**
     * Sets the field value.<p>
     *
     * @param resource the resource
     */
    protected abstract void setResourceValue(CmsResource resource);
}
