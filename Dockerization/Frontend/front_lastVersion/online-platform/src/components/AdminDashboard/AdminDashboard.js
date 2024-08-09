import React from 'react';
import ManageUsers from './ManageUsers';
import ReviewCourses from './ReviewCourses';
import PlatformStatistics from './PlatformStatistics';

function AdminDashboard() {
    return (
        <div className="container mt-5">
            <h1 className="mb-4">Admin Dashboard</h1>

            <div className="row">
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Manage Users</h5>
                            <ManageUsers />
                        </div>
                    </div>
                </div>

                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Review Courses</h5>
                            <ReviewCourses />
                        </div>
                    </div>
                </div>

                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Platform Statistics</h5>
                            <PlatformStatistics />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminDashboard;
