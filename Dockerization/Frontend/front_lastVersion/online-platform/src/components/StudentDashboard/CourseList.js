import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CourseDetails from './CourseDetails';

function CourseList() {
    const [courses, setCourses] = useState([]);

    useEffect(() => {
        axios.get('/api/courses')
            .then(response => setCourses(response.data))
            .catch(error => console.error("Error fetching courses", error));
    }, []);

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">List</h5>
                <ul className="list-group">
                    {courses.map(course => (
                        <li key={course.id} className="list-group-item">
                            {course.name}
                            <CourseDetails courseId={course.id} />
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default CourseList;
