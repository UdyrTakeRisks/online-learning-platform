import React from 'react';
import ManageTestCenters from './ManageTestCenters';
import ManageExams from './ManageExams';

function TestCenterDashboard() {
    return (
        <div className="container mt-5">
            <h1 className="mb-4">Test Center Dashboard</h1>

            <div className="row">
                <div className="col-md-6 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Manage Test Centers</h5>
                            <ManageTestCenters />
                        </div>
                    </div>
                </div>

                <div className="col-md-6 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Manage Exams</h5>
                            <ManageExams />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TestCenterDashboard;
