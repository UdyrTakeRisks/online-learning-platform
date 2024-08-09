import axios from 'axios';

export const registerInstructor = async (instructor) => {
    try {
        const response = await axios.post('/api/instructor/register', instructor);
        console.log("Instructor registration successful:", response.data);
    } catch (error) {
        console.error("Instructor registration failed", error);
        throw error;
    }
};
