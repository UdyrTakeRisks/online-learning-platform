import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function InstructorController() {
    const [message, setMessage] = useState('');
    const [loggedIn, setLoggedIn] = useState(false);
    const [courses, setCourses] = useState([]);
    const [courseName, setCourseName] = useState('');
    const [courseDescription, setCourseDescription] = useState('');

    useEffect(() => {
        // Check if user is logged in
        const isLoggedIn = localStorage.getItem('loggedIn') === 'true';
        setLoggedIn(isLoggedIn);
        if (isLoggedIn) {
            viewCourseInfo();
        }
    }, []);

    const createCourse = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/create/course', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name: courseName, description: courseDescription })
            });
            const data = await response.json();
            setMessage(data.message);
            viewCourseInfo();  // Refresh courses list
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const viewCourseInfo = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/view/courses/info');
            const data = await response.json();
            if (data.message) {
                setMessage(data.message);
            } else {
                setCourses(data);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const notifyCourseEnrollments = async () => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/notify/course/enrollments');
            const data = await response.json();
            if (data.message) {
                setMessage(data.message);
            } else {
                // Handle notification data
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const acceptStudentEnrollment = async (studentName, courseName) => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/accept/student/enrollment', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ studentName, courseName })
            });
            const data = await response.json();
            setMessage(data.message);
            viewCourseInfo();  // Refresh courses list
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const rejectStudentEnrollment = async (studentName, courseName) => {
        try {
            const response = await fetch('http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/reject/student/enrollment', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ studentName, courseName })
            });
            const data = await response.json();
            setMessage(data.message);
            viewCourseInfo();  // Refresh courses list
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className="container">
            <h1>Instructor Controller</h1>
            {loggedIn ? (
                <>
                    <button className="btn btn-primary mb-3" onClick={notifyCourseEnrollments}>Notify Course Enrollments</button>
                    <h2>Courses</h2>
                    {courses.map(course => (
                        <div key={course.id} className="card mb-3">
                            <div className="card-body">
                                <h5 className="card-title">{course.name}</h5>
                                <p className="card-text">{course.description}</p>
                                <p className="card-text">Enrolled Students: {course.enrolledStudents}</p>
                                <button className="btn btn-success me-2" onClick={() => acceptStudentEnrollment('studentName', course.name)}>Accept Enrollment</button>
                                <button className="btn btn-danger" onClick={() => rejectStudentEnrollment('studentName', course.name)}>Reject Enrollment</button>
                            </div>
                        </div>
                    ))}
                    <hr />
                    <h2>Create Course</h2>
                    <div className="mb-3">
                        <label htmlFor="courseName" className="form-label">Course Name</label>
                        <input type="text" className="form-control" id="courseName" value={courseName} onChange={e => setCourseName(e.target.value)} />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="courseDescription" className="form-label">Course Description</label>
                        <textarea className="form-control" id="courseDescription" rows="3" value={courseDescription} onChange={e => setCourseDescription(e.target.value)}></textarea>
                    </div>
                    <button className="btn btn-primary" onClick={createCourse}>Create Course</button>
                    <hr />
                </>
            ) : (
                <p>Please log in to access instructor functionalities.</p>
            )}
            <p>{message}</p>
        </div>
    );
}

export default InstructorController;
