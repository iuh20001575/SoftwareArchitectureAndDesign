import { create } from 'zustand';

export interface PersonalInfo {
    id: string;
    name: string;
    phone: string;
    dob: string;
}

export interface PersonalInfoStore {
    personalInfo: PersonalInfo | null;
    setPersonalInfo: (personalInfo: PersonalInfo) => void;
}

const usePersonalInfoStore = create<PersonalInfoStore>((set) => ({
    personalInfo: null,
    setPersonalInfo: (personalInfo: PersonalInfo) => set({ personalInfo }),
}));

export default usePersonalInfoStore;
