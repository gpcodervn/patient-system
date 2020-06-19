import React, {Component} from 'react';
import {InputText} from "primereact/inputtext";
import {InputMask} from "primereact/inputmask";
import {RadioButton} from "primereact/radiobutton";
import {Growl} from "primereact/growl";
import PropTypes from "prop-types";
import {VIEW} from "../constants/actionMode";
import {PATIENT} from "../model/PatternModel";

class PatientForm extends Component {

    onChange = event => {
        this.notifyChange(event.target.name, event.target.value);
    };

    notifyChange = (field, value) => {
        if (this.props.onChange) {
            this.props.onChange(field, value);
        }
    };

    render() {
        const {errors, model, mode} = this.props;
        return (
            <div className="p-fluid" style={{padding: 20}}>
                <Growl ref={(el) => this.growl = el} />
                <div className={'p-formgrid p-grid ' + (mode === VIEW ? 'gp-form-disabled' : '')}>
                    <div className="p-col-12 p-formgroup-inline">
                        <div className="p-field p-col-6 p-md-3">
                            <label htmlFor="firstName" className="p-sr-only">First name <span className="gp-required">(*)</span></label>
                            <InputText disabled={mode === VIEW} id="firstName" name="firstName" type="text" value={model.firstName} placeholder="First name" onChange={this.onChange} />
                            { errors['firstName'] && (<span className="gp-error">{errors['firstName'][0].message}</span>) }
                        </div>
                        <div className="p-field p-col-6 p-md-3">
                            <label htmlFor="lastName" className="p-sr-only">Last name <span className="gp-required">(*)</span></label>
                            <InputText disabled={mode === VIEW} id="lastName" name="lastName" type="text" value={model.lastName} placeholder="Last name" onChange={this.onChange} />
                            { errors['lastName'] && (<span className="gp-error">{errors['lastName'][0].message}</span>) }
                        </div>
                        <div className="p-field p-col-6 p-md-3">
                            <label htmlFor="lastName" className="p-sr-only">Middle name</label>
                            <InputText disabled={mode === VIEW} id="middleName" name="middleName" type="text" value={model.middleName} placeholder="Middle name" onChange={this.onChange} />
                            { errors['middleName'] && (<span className="gp-error">{errors['middleName'][0].message}</span>) }
                        </div>
                    </div>
                    <div className="p-col-12 p-formgroup-inline">
                        <div className="p-field p-col-6 p-md-3">
                            <label htmlFor="patientId" className="p-sr-only">Patient Id <span className="gp-required">(*)</span></label>
                            <InputText disabled={mode === VIEW} id="patientId" name="patientId" type="text" value={model.patientId} placeholder="Patient Id" onChange={this.onChange} />
                            { errors['patientId'] && (<span className="gp-error">{errors['patientId'][0].message}</span>) }
                        </div>
                        <div className="p-field p-col-6 p-md-3">
                            <label htmlFor="dob" className="p-sr-only">Date of birth <span className="gp-required">(*)</span></label>
                            <InputMask disabled={mode === VIEW} id="dob" name="dob" mask="99/99/9999" value={model.dob} slotChar="mm/dd/yyyy" placeholder="mm/dd/yyyy" onChange={this.onChange} />
                            { errors['dob'] && (<span className="gp-error">{errors['dob'][0].message}</span>) }
                        </div>
                    </div>
                    <div className="p-col-12 p-formgroup-inline">
                        <div className="p-field-checkbox">
                            <label className="p-col-fixed">Gender <span className="gp-required">(*)</span></label>
                        </div>
                        <div className="p-field-checkbox">
                            <RadioButton disabled={mode === VIEW} inputId="male" name="gender" value="M" onChange={(e) => this.notifyChange('gender', e.value)} checked={model.gender === 'M'} />
                            <label htmlFor="male">Male</label>
                        </div>
                        <div className="p-field-checkbox">
                            <RadioButton disabled={mode === VIEW} inputId="female" name="gender" value="F" onChange={(e) => this.notifyChange('gender', e.value)} checked={model.gender === 'F'} />
                            <label htmlFor="female">Female</label>
                        </div>
                        <div className="p-field-checkbox">
                            <RadioButton disabled={mode === VIEW} inputId="other" name="gender" value="O" onChange={(e) => this.notifyChange('gender', e.value)} checked={model.gender === 'O'} />
                            <label htmlFor="other">Female</label>
                        </div>
                        { errors['gender'] && (<span className="gp-error">{errors['gender'][0].message}</span>) }
                    </div>
                </div>
            </div>
        );
    }
}

PatientForm.propTypes = {
    model: PropTypes.object.isRequired,
    errors: PropTypes.object,
    onChange: PropTypes.func,
    mode: PropTypes.string,
};

PatientForm.defaultProps = {
    patient: {...PATIENT},
    errors: {},
    mode: VIEW
};

export default PatientForm;
