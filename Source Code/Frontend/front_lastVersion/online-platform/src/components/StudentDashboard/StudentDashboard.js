import React from 'react';
import CourseList from './CourseList';
import EnrollmentHistory from './EnrollmentHistory';

function StudentDashboard() {
    return (
        <div className="container mt-5">
            <h1 className="mb-4">Student Dashboard</h1>

            <div className="row">
                <div className="col-md-6 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Course List</h5>
                            <CourseList />
                        </div>
                    </div>
                </div>

                <div className="col-md-6 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Enrollment History</h5>
                            <EnrollmentHistory />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default StudentDashboard;
