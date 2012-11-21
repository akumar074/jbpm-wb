package org.jbpm.console.ng.client.editors.process.instance.details.variable.history;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jbpm.console.ng.client.i18n.Constants;
import org.jbpm.console.ng.client.util.ResizableHeader;
import org.jbpm.console.ng.shared.model.VariableSummary;
import org.uberfire.client.workbench.widgets.events.NotificationEvent;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

@Dependent
@Templated(value = "VariableHistoryViewImpl.html")
public class VariableHistoryViewImpl extends Composite implements
        VariableHistoryPresenter.PopupView {

    private long processInstanceId;
    private String variableId;
    
    private VariableHistoryPresenter presenter;
    
    @Inject
    @DataField
    public Label variableNameText;
    @Inject
    @DataField
    public Button closeButton;
    @Inject
    @DataField
    public DataGrid<VariableSummary> processVarListGrid;
    @Inject
    @DataField
    public SimplePager pager;
    
    
    @Inject
    private Event<NotificationEvent> notification;
    private Constants constants = GWT.create(Constants.class);

    @Override
    public void init(VariableHistoryPresenter presenter) {
        this.presenter = presenter;
        
        processVarListGrid.setWidth("400px");
        processVarListGrid.setHeight("200px");

        // Set the message to display when the table is empty.
        processVarListGrid.setEmptyTableWidget(new Label(constants.No_Process_Instances_Available()));

        // Create a Pager to control the table.

        pager.setDisplay(processVarListGrid);
        pager.setPageSize(6);
        
        // Value.
        Column<VariableSummary, String> valueColumn =
                new Column<VariableSummary, String>(new TextCell()) {
                    @Override
                    public String getValue(VariableSummary object) {
                        return object.getNewValue();
                    }
                };

        processVarListGrid.addColumn(valueColumn,
                new ResizableHeader(constants.Value(), processVarListGrid, valueColumn));
        
        // Value.
        Column<VariableSummary, String> oldValueColumn =
                new Column<VariableSummary, String>(new TextCell()) {
                    @Override
                    public String getValue(VariableSummary object) {
                        return object.getOldValue();
                    }
                };

        processVarListGrid.addColumn(oldValueColumn,
                new ResizableHeader(constants.Old_Value(), processVarListGrid, oldValueColumn));
        
        // Last Time Changed Date.
        Column<VariableSummary, String> dueDateColumn = new Column<VariableSummary, String>(new TextCell()) {
            @Override
            public String getValue(VariableSummary object) {

                return object.getTimestamp();

            }
        };
        dueDateColumn.setSortable(true);

        processVarListGrid.addColumn(dueDateColumn,
                new ResizableHeader(constants.Last_Time_Changed(), processVarListGrid, dueDateColumn));
        
        presenter.addDataDisplay(processVarListGrid);
    }

    public void displayNotification(String text) {
        notification.fire(new NotificationEvent(text));
    }

    @Override
    public void setProcessInstanceId(long processInstanceId) {
        this.processInstanceId = processInstanceId;
        
    }
    
    @Override
    public void setVariableId(String variableId) {
        this.variableId = variableId;
        this.variableNameText.setText(variableId);
    }
    
    @EventHandler("closeButton")
    public void closeButton(ClickEvent e) {
        presenter.close();
    }

    public long getProcessInstanceId() {
        return processInstanceId;
    }

    public String getVariableId() {
        return variableId;
    }
    
    

}
