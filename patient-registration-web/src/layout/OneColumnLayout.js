import React from 'react';
import Header from "./Header";
import Footer from "./Footer";

const OneColumnLayout = (props) => {

    document.title = 'Patient Registration System';

    return (
        <div className="dashboard-wrapper">
            <Header />
            <div className="p-fluid gp-content" style={{padding: 20}}>
                {props.children}
            </div>
            <Footer />
        </div>
    );
};

export default OneColumnLayout;