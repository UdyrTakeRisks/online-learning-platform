import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const InstructorDashboard = () => {
    const [message, setMessage] = useState('');
    const [name, setName] = useState('');
    const [duration, setDuration] = useState(0);
    const [category, setCategory] = useState('');
    const [capacity, setCapacity] = useState(0);
    const [courses, setCourses] = useState([]);
    const handleCreateCourse = async (e) => {
        e.preventDefault();
        const course = { name, duration, category, capacity };

        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/create/course', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(course)
            });

            if (response.ok) {
                const data = await response.json();
                if (data && data.message === 'Course created successfully') {
                    setMessage('Course created successfully');
                    handleViewCourses(); // Refresh the list of courses
                } else {
                    throw new Error(data ? data.message : 'Unknown error');
                }
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('Course creation failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };

    const handleViewCourses = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/view/courses/info');

            if (response.ok) {
                const data = await response.json();
                console.log('Fetched courses data:', data); // Debugging log
                setCourses(data.courses || []); // Ensure correct data format
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('View courses failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };


    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/logout', {
                method: 'POST'
            });

            if (response.ok) {
                setMessage('Logged out successfully');
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('Logout failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };



    const handleNotifyEnrollments = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/notify/course/enrollments');

            if (response.ok) {
                const data = await response.json();
                setMessage(JSON.stringify(data));
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('Notify enrollments failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };

    const handleAcceptEnrollment = async (studentName, courseName) => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/accept/student/enrollment', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ studentName, courseName })
            });

            if (response.ok) {
                const data = await response.json();
                setMessage(data.message);
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('Accept enrollment failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };

    const handleRejectEnrollment = async (studentName, courseName) => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/reject/student/enrollment', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ studentName, courseName })
            });

            if (response.ok) {
                const data = await response.json();
                setMessage(data.message);
            } else {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        } catch (error) {
            console.error('Reject enrollment failed:', error.message);
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Instructor Dashboard</h2>
            {message && <div className="alert alert-info">{message}</div>}

            <div className="mb-3">
                <button onClick={handleLogout} className="btn btn-secondary">Logout</button>
            </div>

            <div className="mb-3">
                <h3>Create Course</h3>
                <form onSubmit={handleCreateCourse}>
                    <div className="form-group">
                        <label htmlFor="courseName">Course Name</label>
                        <input
                            type="text"
                            id="courseName"
                            value={name}
                            onChange={e => setName(e.target.value)}
                            placeholder="Enter course name"
                            className="form-control"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="courseDuration">Course Duration</label>
                        <input
                            type="number"
                            id="courseDuration"
                            value={duration}
                            onChange={e => setDuration(parseInt(e.target.value))}
                            placeholder="Enter course duration"
                            className="form-control"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="courseCategory">Course Category</label>
                        <input
                            type="text"
                            id="courseCategory"
                            value={category}
                            onChange={e => setCategory(e.target.value)}
                            placeholder="Enter course category"
                            className="form-control"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="courseCapacity">Course Capacity</label>
                        <input
                            type="number"
                            id="courseCapacity"
                            value={capacity}
                            onChange={e => setCapacity(parseInt(e.target.value))}
                            placeholder="Enter course capacity"
                            className="form-control"
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary mt-2">Create Course</button>
                </form>
            </div>

            <div className="mb-3">
                <h3>View Courses</h3>
                <button onClick={handleViewCourses} className="btn btn-primary">View Courses</button>
            </div>

            <div className="mb-3">
                <h3>Notify Course Enrollments</h3>
                <button onClick={handleNotifyEnrollments} className="btn btn-primary">Notify Enrollments</button>
            </div>
        </div>
    );
};

export default InstructorDashboard;
