import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PlatformStatistics() {
    const [statistics, setStatistics] = useState({ users: 0, courses: 0, enrollments: 0 });

    useEffect(() => {
        axios.get('/api/statistics')
            .then(response => setStatistics(response.data))
            .catch(error => console.error("Error fetching statistics", error));
    }, []);

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title"> Statistics</h5>
                <div className="list-group">
                    <div className="list-group-item">
                        <h6 className="mb-1">Users</h6>
                        <p className="mb-1">{statistics.users}</p>
                    </div>
                    <div className="list-group-item">
                        <h6 className="mb-1">Courses</h6>
                        <p className="mb-1">{statistics.courses}</p>
                    </div>
                    <div className="list-group-item">
                        <h6 className="mb-1">Enrollments</h6>
                        <p className="mb-1">{statistics.enrollments}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PlatformStatistics;
