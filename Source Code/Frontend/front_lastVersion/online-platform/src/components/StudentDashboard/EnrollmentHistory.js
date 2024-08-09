import React from 'react';

function EnrollmentHistory() {
    // Mock enrollment history data
    const enrollmentHistory = [
        { id: 1, courseName: 'Course 1', enrollmentDate: '2022-01-01' },
        { id: 2, courseName: 'Course 2', enrollmentDate: '2022-02-15' },
        { id: 3, courseName: 'Course 3', enrollmentDate: '2022-03-20' }
    ];

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">History</h5>
                <ul className="list-group">
                    {enrollmentHistory.map(enrollment => (
                        <li key={enrollment.id} className="list-group-item">
                            <div className="d-flex justify-content-between">
                                <span>{enrollment.courseName}</span>
                                <span>{enrollment.enrollmentDate}</span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default EnrollmentHistory;
