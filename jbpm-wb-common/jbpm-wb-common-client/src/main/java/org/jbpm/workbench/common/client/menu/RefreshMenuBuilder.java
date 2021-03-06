/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.workbench.common.client.menu;

import elemental2.dom.HTMLElement;
import org.jboss.errai.ioc.client.container.IOC;
import org.uberfire.client.views.pfly.widgets.Button;
import org.uberfire.ext.widgets.common.client.resources.i18n.CommonConstants;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.MenuItem;
import org.uberfire.workbench.model.menu.impl.BaseMenuCustom;

public class RefreshMenuBuilder implements MenuFactory.CustomMenuBuilder {

    protected Button menuRefreshButton;
    private SupportsRefresh supportsRefresh;

    public RefreshMenuBuilder(final SupportsRefresh supportsRefresh) {
        this.supportsRefresh = supportsRefresh;
        setupMenuButton();
    }

    @Override
    public void push(MenuFactory.CustomMenuBuilder element) {
    }

    @Override
    public MenuItem build() {
        return new BaseMenuCustom<HTMLElement>() {
            @Override
            public HTMLElement build() {
                return menuRefreshButton.getElement();
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void setEnabled(boolean enabled) {

            }
        };
    }

    public void setupMenuButton() {
        menuRefreshButton = IOC.getBeanManager().lookupBean(Button.class).newInstance();
        menuRefreshButton.setButtonStyleType(Button.ButtonStyleType.LINK);
        menuRefreshButton.addIcon("fa",
                                  "fa-refresh");
        menuRefreshButton.getElement().title = CommonConstants.INSTANCE.Refresh();
        menuRefreshButton.setClickHandler(() -> supportsRefresh.onRefresh());
    }

    public interface SupportsRefresh {

        void onRefresh();
    }
}