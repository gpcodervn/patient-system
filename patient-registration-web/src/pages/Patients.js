import React, {Component} from 'react';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Growl} from 'primereact/growl';
import {Button} from 'primereact/button';
import ApiService from "../services/ApiService";
import {PATIENTS_URL} from "../constants/apiUrl";
import PatientFilter from "../components/PatientFilter";
import queryString from 'query-string';
import {withRouter} from 'react-router-dom';
import ConfirmDialog from "../components/ConfirmDialog";
import {ROWS, ROWS_PER_PAGE_OPTIONS} from "../constants/paging";

class Patients extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            patients: [],
            loading: true,
            rows: ROWS,
            first: 0,
            page: 0,
            totalRecords: 0,
            keywords: '',
            sortField: 'patientId',
            sortOrder: -1,
            selectedPatient: {},
            showConfirmDelete: false,
        };

        this.apiService = new ApiService();
    }

    componentDidMount() {
        this.getPatients();
    }

    getPatients = () => {
        let queryParams = this.buildQueryParams();
        this.apiService.get(`${PATIENTS_URL}${queryParams}`).then(res => {
            let pageable = res.data || {};
            this.setState({
                totalRecords: pageable.totalElements || 0,
                patients: pageable.content || [],
                loading: false,
            });
        }).catch(e => {
            console.log('Can not get patients ' + e);
            this.setState({loading: false});
            this.showError('Can not get patients');
        });
    };

    buildQueryParams = () => {
        let { keywords, sortField, sortOrder, page, rows: limit } = this.state;
        let orders = [];
        orders.push((sortOrder === 1 ? '+' : '-') + sortField);
        console.log('orders: ' + orders);
        return `?${keywords}&page=${page}&limit=${limit}&${queryString.stringify({sort: orders})}`;
    };

    onPage = event => {
        console.log(event);
        this.setState({
            loading: true,
            rows: event.rows,
            page: event.page,
            first: event.first
        }, this.getPatients);
    };

    onSort = event => {
        console.log(event);
        this.setState({
                sortField: event.sortField,
                sortOrder: event.sortOrder,
            }, this.getPatients
        );
    };

    onSearch = params => {
        console.log('onSearch: ' + queryString.stringify(params, { skipNull: true, skipEmptyString: true }));
        this.setState({ keywords: queryString.stringify(params, { skipNull: true, skipEmptyString: true }) }, this.getPatients);
    };

    showError = (msg) => {
        this.growl.show({severity: 'error', summary: 'Error', detail: msg});
    };

    showSuccess = (msg) => {
        this.growl.show({severity: 'success', summary: 'Success', detail: msg});
    };

    onAdd = () => {
        this.props.history.push("/add");
    };

    onSelect = (patient) => {
        this.props.history.push("/view/" + patient.id, { patient: patient });
    };

    onEdit = (patient) => {
        this.props.history.push("/edit/" + patient.id, { patient: patient });
    };

    onDelete = (patient) => {
        this.setState({selectedPatient : patient, showConfirmDelete: true});
    };

    doDelete = () => {
        this.hideConfirmDialog();
        const {selectedPatient} = this.state;
        console.log("Deleting patient id [" + selectedPatient.id + "]");
        this.setState({loading: true});
        this.apiService.delete(`${PATIENTS_URL}/${selectedPatient.id}`).then(() => {
            this.getPatients();
            this.showSuccess('Deleted patient successfully!');
        }).catch(e => {
            console.log('Can not update patient ' + JSON.stringify(e));
            this.setState({loading: false});
            this.showError(`Can not delete patient because of "${e.message}"`);
        });
    };

    hideConfirmDialog = () => {
        this.setState({showConfirmDelete : false});
    };

    rowIndexTemplate = (rowData, column) => {
        return column.rowIndex + 1 + "";
    };

    rowActionTemplate = (rowData, column) => {
        return (
            rowData.softDeleted === true
                ? null
                : (
                    <span>
                        <Button onClick={() => this.onSelect(rowData)} icon="pi pi-eye" iconPos="left" className="p-button-raised p-button-success" title="View" />
                        <span> | </span>
                        <Button onClick={() => this.onEdit(rowData)} icon="pi pi-pencil" iconPos="left" className="p-button-raised p-button-warning" title="Edit" />
                        <span> | </span>
                        <Button onClick={() => this.onDelete(rowData)} icon="pi pi-trash" iconPos="left" className="p-button-raised p-button-danger" title="Delete" />
                    </span>
                )
        );
    };

    render() {
        return (
            <div>
                <Growl ref={(el) => this.growl = el} />
                <div className="p-col-12">
                    <PatientFilter onSearch={this.onSearch} />
                </div>
                <div className="p-col-12" style={{textAlign: 'right'}}>
                    <Button label="Add" onClick={this.onAdd} icon="pi pi-check" iconPos="left" className="p-button-raised" style={{width:'100px'}} />
                </div>
                <div className="p-col-12">
                    <DataTable value={this.state.patients} in
                               ref={(el) => this.dt = el}
                               responsive={true}
                               paginator={true} rows={this.state.rows} totalRecords={this.state.totalRecords} rowsPerPageOptions={ROWS_PER_PAGE_OPTIONS}
                               paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                               currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                               sortable={true} sortMode="single" onSort={this.onSort}
                               sortField={this.state.sortField} sortOrder={this.state.sortOrder}
                               lazy={true} first={this.state.first} onPage={this.onPage} loading={this.state.loading}>
                        <Column header="#" body={this.rowIndexTemplate} style={{textAlign: 'center', width: '5%'}} />
                        <Column field="patientId" header="Patient ID" sortable={true} />
                        <Column field="firstName" header="First Name" sortable={true} />
                        <Column field="middleName" header="Middle Name" sortable={true} />
                        <Column field="lastName" header="Last Name" sortable={true} />
                        <Column field="dob" header="Date of Birth" sortable={true} style={{textAlign: 'center'}} />
                        <Column field="gender" header="Gender" sortable={true} style={{textAlign: 'center', width: '7%'}}/>
                        <Column header="Action" style={{textAlign: 'center'}} body={this.rowActionTemplate.bind(this)} />
                    </DataTable>
                </div>
                <ConfirmDialog visible={this.state.showConfirmDelete} onHide={this.hideConfirmDialog} onOk={this.doDelete} />
            </div>
        );
    }
}

export default withRouter(Patients);
