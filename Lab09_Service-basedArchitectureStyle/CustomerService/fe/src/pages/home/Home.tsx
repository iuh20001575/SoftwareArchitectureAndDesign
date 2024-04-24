import { useLayoutEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../configs';
import usePersonalInfoStore, {
    PersonalInfoStore,
} from '../../stores/personalInfoStore';

const Home = () => {
    const personalInfo = usePersonalInfoStore(
        (state: PersonalInfoStore) => state.personalInfo,
    );
    const navigate = useNavigate();

    useLayoutEffect(() => {
        // if (!personalInfo) navigate(routes.login);
    }, [navigate, personalInfo]);

    if (!personalInfo) return null;

    return <div>Home page</div>;
};

export default Home;
