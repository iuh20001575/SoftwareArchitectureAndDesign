import axiosClient from '../api/axios';

const authService = {
    login: ({ phone, password }: { phone: string; password: string }) =>
        axiosClient.post('/auth/login', { phone, password }),
    register: ({
        name,
        phone,
        dob,
        password,
    }: {
        phone: string;
        password: string;
        name: string;
        dob: string;
    }) => axiosClient.post('/auth/register', { phone, password, name, dob }),
};

export default authService;
