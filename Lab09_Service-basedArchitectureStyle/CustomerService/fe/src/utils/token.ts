const KEY = 'iuh-kt-sba';

const token = {
    get: () => localStorage.getItem(KEY),
    set: (token: string) => localStorage.setItem(KEY, token),
};

export default token;
