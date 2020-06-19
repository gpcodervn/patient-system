import React, {Component} from 'react';

import {BrowserRouter as Router, Switch} from "react-router-dom";
import Patients from "./pages/Patients";
import AddPatient from "./pages/AddPatient";
import EditPatient from "./pages/EditPatient";
import ViewPatient from "./pages/ViewPatient";
import Error404 from "./pages/404";
import Home from "./pages/Home";
import LayoutBoundaryRoute from "./layout/LayoutBoundaryRoute";
import OneColumnLayout from "./layout/OneColumnLayout";

class App extends Component {

  render() {
    return (
        <Router>
          <Switch>
            <LayoutBoundaryRoute  path="/add" component={AddPatient} layout={OneColumnLayout} />
            <LayoutBoundaryRoute  path="/edit/:id" component={EditPatient} layout={OneColumnLayout} />
            <LayoutBoundaryRoute  path="/view/:id" component={ViewPatient} layout={OneColumnLayout} />
            <LayoutBoundaryRoute  path="/patients" component={Patients} layout={OneColumnLayout} />
            <LayoutBoundaryRoute  path="/404" component={Error404} layout={OneColumnLayout} />
            <LayoutBoundaryRoute  path="/" component={Home} layout={OneColumnLayout} />
          </Switch>
        </Router>
    );
  }
}

export default App;
