import React, {Component} from 'react';
import {Button} from 'primereact/button';
import {InputText} from "primereact/inputtext";
import PropTypes from 'prop-types';
import {RadioButton} from "primereact/radiobutton";
import {InputMask} from "primereact/inputmask";
import {Checkbox} from "primereact/checkbox";

class PatientFilter extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            firstName: '',
            lastName: '',
            patientId: '',
            dob: null,
            gender: '',
            withDeleted: false,
        };
    }

    search = () => {
        if(this.props.onSearch) {
            const { firstName,lastName, patientId, dob, gender, withDeleted } = this.state
            this.props.onSearch({firstName,lastName, patientId, dob, gender, withDeleted});
        }
    };

    onChange = event => {
        this.setState({ [event.target.name]: event.target.value });
    };

    onToggleChecked = () => {
        const {withDeleted} = this.state;
        this.setState({ withDeleted: !withDeleted });
    };

    render() {
        return (
            <div className="p-formgrid p-grid">
                <div className="p-col-12 p-formgroup-inline">
                    <div className="p-field p-col-6 p-md-3">
                        <label htmlFor="firstName" className="p-sr-only">First name</label>
                        <InputText id="firstName" name="firstName" type="text" placeholder="First name" onChange={this.onChange} />
                    </div>
                    <div className="p-field p-col-6 p-md-3">
                        <label htmlFor="lastName" className="p-sr-only">Last name</label>
                        <InputText id="lastName" name="lastName" type="text" placeholder="Last name" onChange={this.onChange} />
                    </div>
                </div>
                <div className="p-col-12 p-formgroup-inline">
                    <div className="p-field p-col-6 p-md-3">
                        <label htmlFor="patientId" className="p-sr-only">Patient Id</label>
                        <InputText id="patientId" name="patientId" type="text" placeholder="Patient Id" onChange={this.onChange} />
                    </div>
                    <div className="p-field p-col-6 p-md-3">
                        <label htmlFor="dob" className="p-sr-only">Date of birth</label>
                        <InputMask id="dob" name="dob" mask="99/99/9999" value={this.state.dob} slotChar="mm/dd/yyyy" placeholder="mm/dd/yyyy" onChange={this.onChange} />
                    </div>
                </div>
                <div className="p-col-12 p-formgroup-inline">
                    <div className="p-field-checkbox">
                        <label className="p-col-fixed">Gender</label>
                    </div>
                    <div className="p-field-checkbox">
                        <RadioButton inputId="all" name="gender" value="" onChange={(e) => this.setState({gender: e.value})} checked={this.state.gender === ''} />
                        <label htmlFor="all">All</label>
                    </div>
                    <div className="p-field-checkbox">
                        <RadioButton inputId="male" name="gender" value="M" onChange={(e) => this.setState({gender: e.value})} checked={this.state.gender === 'M'} />
                        <label htmlFor="male">Male</label>
                    </div>
                    <div className="p-field-checkbox">
                        <RadioButton inputId="female" name="gender" value="F" onChange={(e) => this.setState({gender: e.value})} checked={this.state.gender === 'F'} />
                        <label htmlFor="female">Female</label>
                    </div>
                    <div className="p-field-checkbox">
                        <RadioButton inputId="other" name="gender" value="O" onChange={(e) => this.setState({gender: e.value})} checked={this.state.gender === 'O'} />
                        <label htmlFor="other">Female</label>
                    </div>
                    <div className="p-field p-field-checkbox">
                        <label htmlFor="withDeleted">With deleted</label>
                        <Checkbox inputId="withDeleted" name="withDeleted" value={this.state.withDeleted} onChange={this.onToggleChecked} checked={this.state.withDeleted}/>
                    </div>
                    <div className="p-field">
                        <Button type="button" label="Search" onClick={this.search} style={{width:'100px'}} icon="pi pi-search" iconPos="left" className="p-button-raised p-button-success" />
                    </div>
                </div>
            </div>
        )
    }
}

PatientFilter.propTypes = {
    onSearch: PropTypes.func.isRequired
};

export default PatientFilter;