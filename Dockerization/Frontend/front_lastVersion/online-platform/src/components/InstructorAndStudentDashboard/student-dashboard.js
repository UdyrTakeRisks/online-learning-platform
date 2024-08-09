// src/student-dashboard.js
import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

// Enrollments Component
function Enrollments({ setMessage }) {
    const handleViewEnrollments = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/view/course/enrollments');
            if (response.ok) {
                const data = await response.json();
                setMessage(JSON.stringify(data));
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div>
            <button onClick={handleViewEnrollments} className="btn btn-primary">View Enrollments</button>
        </div>
    );
}

// CourseEnrollment Component
function CourseEnrollment({ setMessage }) {
    const [courseName, setCourseName] = useState('');

    const handleEnroll = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/make/course/enrollment/${courseName}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });
            const data = await response.json();
            if (response.ok) {
                setMessage(data.message);
            } else {
                throw new Error(data.message || 'Unknown error');
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <form onSubmit={handleEnroll}>
            <div className="form-group">
                <label htmlFor="courseName">Course Name</label>
                <input
                    type="text"
                    className="form-control"
                    id="courseName"
                    value={courseName}
                    onChange={(e) => setCourseName(e.target.value)}
                />
            </div>
            <button type="submit" className="btn btn-primary">Enroll</button>
        </form>
    );
}

// CancelEnrollment Component
function CancelEnrollment({ setMessage }) {
    const [courseName, setCourseName] = useState('');

    const handleCancel = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/cancel/course/enrollment/${courseName}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' }
            });
            const data = await response.json();
            if (response.ok) {
                setMessage(data.message);
            } else {
                throw new Error(data.message || 'Unknown error');
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <form onSubmit={handleCancel}>
            <div className="form-group">
                <label htmlFor="cancelCourseName">Course Name</label>
                <input
                    type="text"
                    className="form-control"
                    id="cancelCourseName"
                    value={courseName}
                    onChange={(e) => setCourseName(e.target.value)}
                />
            </div>
            <button type="submit" className="btn btn-danger">Cancel Enrollment</button>
        </form>
    );
}

// CourseReview Component
function CourseReview({ setMessage }) {
    const [review, setReview] = useState({
        name: '',
        rating: '',
        description: ''
    });

    const handleReview = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/make/course/review', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(review)
            });
            const data = await response.json();
            if (response.ok) {
                setMessage(data.message);
            } else {
                throw new Error(data.message || 'Unknown error');
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setReview({ ...review, [name]: value });
    };

    return (
        <form onSubmit={handleReview}>
            <div className="form-group">
                <label htmlFor="reviewName">Course Name</label>
                <input
                    type="text"
                    className="form-control"
                    id="reviewName"
                    name="name"
                    value={review.name}
                    onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <label htmlFor="reviewRating">Rating</label>
                <input
                    type="number"
                    className="form-control"
                    id="reviewRating"
                    name="rating"
                    value={review.rating}
                    onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <label htmlFor="reviewDescription">Description</label>
                <textarea
                    className="form-control"
                    id="reviewDescription"
                    name="description"
                    value={review.description}
                    onChange={handleChange}
                ></textarea>
            </div>
            <button type="submit" className="btn btn-primary">Submit Review</button>
        </form>
    );
}

// SearchTestCenters Component
function SearchTestCenters({ setMessage }) {
    const [location, setLocation] = useState('');

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/search/available/test-centers/${location}`);
            if (response.ok) {
                const data = await response.text();
                setMessage(data);
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <form onSubmit={handleSearch}>
            <div className="form-group">
                <label htmlFor="testCenterLocation">Test Center Location</label>
                <input
                    type="text"
                    className="form-control"
                    id="testCenterLocation"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                />
            </div>
            <button type="submit" className="btn btn-primary">Search Test Centers</button>
        </form>
    );
}

// ExamRegistration Component
function ExamRegistration({ setMessage }) {
    const [examName, setExamName] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/register/exam/${examName}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });
            const data = await response.json();
            if (response.ok) {
                setMessage(data.message);
            } else {
                throw new Error(data.message || 'Unknown error');
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <form onSubmit={handleRegister}>
            <div className="form-group">
                <label htmlFor="examName">Exam Name</label>
                <input
                    type="text"
                    className="form-control"
                    id="examName"
                    value={examName}
                    onChange={(e) => setExamName(e.target.value)}
                />
            </div>
            <button type="submit" className="btn btn-primary">Register</button>
        </form>
    );
}

// ExamGradesHistory Component
function ExamGradesHistory({ setMessage }) {
    const handleViewGrades = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/view/exam/grades/history');
            if (response.ok) {
                const data = await response.json();
                setMessage(JSON.stringify(data));
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div>
            <button onClick={handleViewGrades} className="btn btn-primary">View Exam Grades History</button>
        </div>
    );
}

// Notification Component
function Notification({ setMessage }) {
    const handleNotify = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/notify/course/enrollments');
            if (response.ok) {
                const data = await response.json();
                setMessage(JSON.stringify(data));
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div>
            <button onClick={handleNotify} className="btn btn-primary">Notify for Course Enrollments
            </button>
        </div>
    );
}

// Logout Component
function Logout({ setMessage }) {
    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/logout', {
                method: 'POST'
            });
            if (response.ok) {
                const data = await response.text();
                setMessage(data);
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div>
            <button onClick={handleLogout} className="btn btn-danger">Logout</button>
        </div>
    );
}

// Main App Component
function App() {
    const [message, setMessage] = useState('');

    return (
        <div className="container">
            <h1>Student Dashboard</h1>
            <div className="alert alert-info">{message}</div>
            <Enrollments setMessage={setMessage} />
            <CourseEnrollment setMessage={setMessage} />
            <CancelEnrollment setMessage={setMessage} />
            <CourseReview setMessage={setMessage} />
            <SearchTestCenters setMessage={setMessage} />
            <ExamRegistration setMessage={setMessage} />
            <ExamGradesHistory setMessage={setMessage} />
            <Notification setMessage={setMessage} />
            <Logout setMessage={setMessage} />
        </div>
    );
}

export default App;

